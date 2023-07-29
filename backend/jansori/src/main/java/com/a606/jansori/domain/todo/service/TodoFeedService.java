package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.FeedMemberDto;
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
import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.domain.todo.repository.TodoRepository;
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

  private static final Integer DEFAULT_PAGE_SIZE = 10;

  @Transactional(readOnly = true)
  public GetTodoFeedResDto getFollowingFeed(Long memberId, Long cursor, Integer size) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    List<Tag> tags = tagFollowRepository.findAllByMember(member).stream()
        .map(TagFollow::getTag)
        .collect(Collectors.toList());

    if (tags == null || tags.size() == 0) {
      throw new TagNotFoundException();
    }

    if (size == null || size < 0) {
      size = DEFAULT_PAGE_SIZE;
    }

    Slice<Todo> pagedTodos = cursor == null ?
        todoRepository.findByFollowingTagsWithoutCursor(tags, PageRequest.of(0, size))
        : todoRepository.findByFollowingTagsWithCursor(tags, cursor, PageRequest.of(0, size));

    return getFeedResDtoFrom(size, member, pagedTodos);
  }

  @Transactional(readOnly = true)
  public GetTodoFeedResDto getTagFeed(Long memberId, Long tagId, Long cursor, Integer size) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    Tag tag = tagRepository.findById(tagId)
        .orElseThrow(TagNotFoundException::new);

    if (size == null || size < 0) {
      size = DEFAULT_PAGE_SIZE;
    }

    Slice<Todo> pagedTodos = cursor == null ?
        todoRepository.findByTagWithoutCursor(tag, PageRequest.of(0, size))
        : todoRepository.findByTagWithCursor(tag, cursor, PageRequest.of(0, size));

    return getFeedResDtoFrom(size, member, pagedTodos);
  }


  private List<FeedDto> convertTodosWithMemberToFeedDto(List<Todo> todos, Member member) {

    return todos.stream().map(todo -> {

      Nag nag = todo.getNag();

      return FeedDto.ofFeedRelatedDto(FeedMemberDto.from(todo.getMember()),
          todo,
          FeedNagDto.fromNagAndUnlocked(
              nag,
              nagUnlockRepository.existsByNagAndMember(nag, member))
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
}
