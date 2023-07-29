package com.a606.jansori.domain.todo.controller;

import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.domain.todo.dto.GetTodoListResDto;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.todo.service.TodoFeedService;
import com.a606.jansori.domain.todo.service.TodoService;
import com.a606.jansori.global.common.EnvelopeResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  private final TodoFeedService todoFeedService;

  @PostMapping
  public EnvelopeResponse postTodo(@Valid @RequestBody PostTodoReqDto postTodoReqDto,
      @RequestParam Long memberId) {

    return EnvelopeResponse.<PostTodoResDto>builder()
        .data(todoService.postTodo(postTodoReqDto, memberId))
        .build();
  }

  @GetMapping("/my/today")
  public EnvelopeResponse getMyTodayTodo(Long memberId) {

    return EnvelopeResponse.<GetTodoListResDto>builder()
        .data(todoService.getMyTodayTodo(memberId))
        .build();

  }

  @GetMapping("/my/all")
  public EnvelopeResponse getMyAllTodo(Long memberId) {

    return EnvelopeResponse.<GetTodoListResDto>builder()
        .data(todoService.getMyAllTodo(memberId))
        .build();

  }

  @GetMapping("/feed/following")
  public EnvelopeResponse<GetTodoFeedResDto> getFollowingFeed(Long memberId, Long cursor,
      Integer size) {

    return EnvelopeResponse.<GetTodoFeedResDto>builder()
        .data(todoFeedService.getFollowingFeed(memberId, cursor, size))
        .build();
  }

  @GetMapping("/feed")
  public EnvelopeResponse<GetTodoFeedResDto> getTagFeed(Long memberId, Long tagId,
      Long cursor, Integer size) {

    return EnvelopeResponse.<GetTodoFeedResDto>builder()
        .data(todoFeedService.getTagFeed(memberId, tagId, cursor, size))
        .build();
  }

}
