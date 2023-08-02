package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.dto.FeedNagDto;
import com.a606.jansori.domain.nag.repository.NagUnlockRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.FeedDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedByFollowingReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedByTagReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoFeedService {

  private final MemberRepository memberRepository;

  private final TodoRepository todoRepository;

  private final TagFollowRepository tagFollowRepository;

  private final TagRepository tagRepository;

  private final NagUnlockRepository nagUnlockRepository;

  private final SecurityUtil securityUtil;

  private final Clock clock;

  @Transactional(readOnly = true)
  public GetTodoFeedResDto getFollowingFeed(
      GetTodoFeedByFollowingReqDto getTodoFeedByFollowingReqDto) {

    Member member = getMemberFromSecurityUtil();

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
  public GetTodoFeedResDto getTagFeed(GetTodoFeedByTagReqDto getTodoFeedByTagReqDto) {

    Member member = getMemberFromSecurityUtil();

    Tag tag = tagRepository.findById(getTodoFeedByTagReqDto.getTagId())
        .orElseThrow(TagNotFoundException::new);

    Long cursor = getTodoFeedByTagReqDto.getCursor();
    Integer size = getTodoFeedByTagReqDto.getSize();
    LocalDate today = LocalDate.now(clock);

    Slice<Todo> pagedTodos = todoRepository.findTodoByTagsAndPages(List.of(tag), cursor,
        PageRequest.of(0, size), today);

    return getFeedResDtoFrom(size, member, pagedTodos);
  }

  private List<FeedDto> convertTodosWithMemberToFeedDto(List<Todo> todos, Member member) {

    return todos.stream().map(todo -> {

      Nag nag = todo.getNag();

      return FeedDto.from(todo,
          FeedNagDto.fromNagAndUnlocked(nag, nagUnlockRepository.existsByNagAndMember(nag, member))
      );
    }).collect(Collectors.toList());

  }

  private GetTodoFeedResDto getFeedResDtoFrom(Integer size, Member member, Slice<Todo> pagedTodos) {

    Boolean hasNext = pagedTodos.hasNext();
    Long nextCursor = hasNext ? pagedTodos.getContent().get(size - 1).getId() : null;
    List<Todo> todos = pagedTodos.getContent();

    return GetTodoFeedResDto.builder()
        .feed(convertTodosWithMemberToFeedDto(todos, member))
        .nextCursor(nextCursor)
        .hasNext(hasNext)
        .build();
  }

  private Member getMemberFromSecurityUtil() {

    return memberRepository.findById(securityUtil.getSessionMemberId())
        .orElseThrow(MemberNotFoundException::new);
  }
}
