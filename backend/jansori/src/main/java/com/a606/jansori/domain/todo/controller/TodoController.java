package com.a606.jansori.domain.todo.controller;

import com.a606.jansori.domain.todo.dto.GetLineDetailsResDto;
import com.a606.jansori.domain.todo.dto.GetTodoByDateReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoByDateResDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.domain.todo.dto.PatchTodoResDto;
import com.a606.jansori.domain.todo.dto.PostPersonaReactResDto;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.todo.service.TodoFeedService;
import com.a606.jansori.domain.todo.service.TodoPersonaService;
import com.a606.jansori.domain.todo.service.TodoService;
import com.a606.jansori.global.common.EnvelopeResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  private final TodoPersonaService todoPersonaService;

  @PostMapping
  public EnvelopeResponse<PostTodoResDto> postTodo(@Valid @RequestBody PostTodoReqDto postTodoReqDto,
      @RequestParam Long memberId) {

    return EnvelopeResponse.<PostTodoResDto>builder()
        .data(todoService.postTodo(postTodoReqDto, memberId))
        .build();
  }

  @GetMapping("/my")
  public EnvelopeResponse<GetTodoByDateResDto> getMyTodayTodo(Long memberId,
      GetTodoByDateReqDto getTodoByDateReqDto) {

    return EnvelopeResponse.<GetTodoByDateResDto>builder()
        .data(todoService.getMyTodoByDate(memberId, getTodoByDateReqDto))
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

  @PatchMapping("/{todoId}")
  public EnvelopeResponse<PatchTodoResDto> patchTodoAccomplishment(Long memberId,
      @PathVariable Long todoId) {

    return EnvelopeResponse.<PatchTodoResDto>builder()
        .data(todoService.patchTodoAccomplishment(memberId, todoId))
        .build();
  }

  @GetMapping("/{todoId}/personas")
  public EnvelopeResponse<GetLineDetailsResDto> getTodoPersonas(@PathVariable Long todoId) {

    return EnvelopeResponse.<GetLineDetailsResDto>builder()
        .data(todoPersonaService.getTodoPersonas(todoId))
        .build();
  }

  @PostMapping("/{todoId}/{todoPersonaId}")
  public EnvelopeResponse<PostPersonaReactResDto> postPersonaReaction(Long memberId,
      @PathVariable Long todoId,
      @PathVariable Long todoPersonaId) {

    return EnvelopeResponse.<PostPersonaReactResDto>builder()
        .data(todoPersonaService.postPersonaReaction(memberId, todoId, todoPersonaId))
        .build();
  }

}
