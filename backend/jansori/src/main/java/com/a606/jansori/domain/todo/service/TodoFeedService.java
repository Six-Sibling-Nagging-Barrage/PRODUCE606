package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagInteraction;
import com.a606.jansori.domain.nag.repository.NagInteractionRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.domain.TodoFeeds;
import com.a606.jansori.domain.todo.dto.GetTodoDetailResDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedByFollowingReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedByTagReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.domain.todo.dto.TodoFeedDto;
import com.a606.jansori.domain.todo.exception.TodoNotFoundException;
import com.a606.jansori.domain.todo.exception.TodoUnauthorizedException;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoFeedService {

  private final TodoRepository todoRepository;

  private final TagFollowRepository tagFollowRepository;

  private final TagRepository tagRepository;

  private final NagInteractionRepository nagInteractionRepository;

  private final SecurityUtil securityUtil;

  private final Clock clock;

  @Transactional(readOnly = true)
  public GetTodoFeedResDto getTodoFeedByFollowingTags(
      GetTodoFeedByFollowingReqDto getTodoFeedByFollowingReqDto) {

    Member member = securityUtil.getCurrentMemberByToken();

    List<Tag> tags = tagFollowRepository.findAllByMember(member).stream()
        .map(TagFollow::getTag)
        .collect(Collectors.toList());

    if (tags.isEmpty()) {
      throw new TagNotFoundException();
    }

    Long cursor = getTodoFeedByFollowingReqDto.getCursor();
    Integer size = getTodoFeedByFollowingReqDto.getSize();
    LocalDate today = LocalDate.now(clock);

    Slice<Todo> pagedTodos = todoRepository.findTodoByTagsAndPages(tags, cursor,
        PageRequest.of(0, size), today);

    return getFeedResDtoFrom(size, member, pagedTodos);
  }

  @Transactional(readOnly = true)
  public GetTodoFeedResDto getTodoFeedByGivenTag(GetTodoFeedByTagReqDto getTodoFeedByTagReqDto) {

    Member member = securityUtil.getNullableMemberByToken();

    Tag tag = tagRepository.findById(getTodoFeedByTagReqDto.getTagId())
        .orElseThrow(TagNotFoundException::new);

    Long cursor = getTodoFeedByTagReqDto.getCursor();
    Integer size = getTodoFeedByTagReqDto.getSize();
    LocalDate today = LocalDate.now(clock);

    Slice<Todo> pagedTodos = todoRepository.findTodoByTagsAndPages(List.of(tag), cursor,
        PageRequest.of(0, size), today);

    return getFeedResDtoFrom(size, member, pagedTodos);
  }

  @Transactional(readOnly = true)
  public GetTodoDetailResDto getTodoDetail(Long todoId) {

    Member member = securityUtil.getNullableMemberByToken();

    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(TodoNotFoundException::new);

    if (!todo.getMember().equals(member) && !todo.getDisplay()) {
      throw new TodoUnauthorizedException();
    }

    if (todo.getNag() == null) {
      return GetTodoDetailResDto.from(todo, false, false);
    }

    Optional<NagInteraction> nagInteraction = nagInteractionRepository
        .findNagInteractionByNagAndMember(todo.getNag(), member);

    return nagInteraction.map(interaction -> GetTodoDetailResDto.from(todo,
        interaction.getNagUnlock(),
        interaction.getNagLike()))
        .orElseGet(() -> GetTodoDetailResDto.from(todo, false, false));
  }

  private List<TodoFeedDto> convertTodosWithMemberToFeedTodoDto(List<Todo> todos, Member member) {

    if (member == null) {
      return todos.stream().map(todo ->
          TodoFeedDto.from(todo, false, false)
      ).collect(Collectors.toList());
    }
    List<Nag> nags = todos.stream().map(Todo::getNag).collect(Collectors.toList());
    List<NagInteraction> nagInteractions = nagInteractionRepository
        .findNagInteractionsByNagIsInAndMember(nags, member);

    TodoFeeds todoFeeds = new TodoFeeds(todos, nagInteractions);

    return todoFeeds.getTodoFeeds();
  }

  private GetTodoFeedResDto getFeedResDtoFrom(Integer size, Member member, Slice<Todo> pagedTodos) {

    Boolean hasNext = pagedTodos.hasNext();
    Long nextCursor = hasNext ? pagedTodos.getContent().get(size - 1).getId() : null;
    List<Todo> todos = pagedTodos.getContent();

    return GetTodoFeedResDto.builder()
        .feed(convertTodosWithMemberToFeedTodoDto(todos, member))
        .nextCursor(nextCursor)
        .hasNext(hasNext)
        .build();
  }
}
