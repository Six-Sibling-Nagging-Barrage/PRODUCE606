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
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.FeedDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.domain.todo.dto.TodoDto;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoFeedService {

  private final MemberRepository memberRepository;

  private final TodoRepository todoRepository;

  private final TagFollowRepository tagFollowRepository;

  private final NagUnlockRepository nagUnlockRepository;

  public GetTodoFeedResDto getFollowingFeed(Long memberId, Long cursor, Pageable pageable) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    List<Tag> tags = tagFollowRepository.findAllByMember(member).stream()
        .map(TagFollow::getTag)
        .collect(Collectors.toList());

    Slice<Todo> pagedTodos = todoRepository.findFollowingFeed(tags, cursor, pageable);

    return GetTodoFeedResDto.builder()
        .feed(convertTodosToFeedDtos(pagedTodos.getContent()))
        .nextCursor(pagedTodos.nextPageable().getOffset())
        .hasNext(pagedTodos.hasNext())
        .build();
  }

  private List<FeedDto> convertTodosToFeedDtos(List<Todo> todos) {

    return todos.stream().map(todo -> {

      Nag nag = todo.getNag();
      FeedNagDto feedNagDto = FeedNagDto.ofMemberAndLikeCount(nag.getMember(), nag.getLikeCount());
      feedNagDto.setNagContentByUnlocked(
          nagUnlockRepository.existsByNagAndMember(nag, todo.getMember()), nag);

      return FeedDto.ofFeedRelatedDto(FeedMemberDto.from(todo.getMember()),
          TodoDto.from(todo),
          feedNagDto);

    }).collect(Collectors.toList());
  }
}
