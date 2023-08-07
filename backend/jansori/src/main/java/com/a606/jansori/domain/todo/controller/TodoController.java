package com.a606.jansori.domain.todo.controller;

import com.a606.jansori.domain.todo.dto.GetTodoByDateReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoByDateResDto;
import com.a606.jansori.domain.todo.dto.GetTodoDetailResDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedByFollowingReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedByTagReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.domain.todo.dto.GetTodoMonthlyExistenceReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoMonthlyExistenceResDto;
import com.a606.jansori.domain.todo.dto.GetTodoPersonaDetailsResDto;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  private final TodoFeedService todoFeedService;

  private final TodoPersonaService todoPersonaService;

  @PostMapping
  public EnvelopeResponse<PostTodoResDto> postTodo(
      @Valid @RequestBody PostTodoReqDto postTodoReqDto) {

    return EnvelopeResponse.<PostTodoResDto>builder()
        .data(todoService.postTodo(postTodoReqDto))
        .build();
  }

  @GetMapping("/my")
  public EnvelopeResponse<GetTodoByDateResDto> getMyTodoByDate(
      @Valid GetTodoByDateReqDto getTodoByDateReqDto) {

    return EnvelopeResponse.<GetTodoByDateResDto>builder()
        .data(todoService.getMyTodoByDate(getTodoByDateReqDto))
        .build();
  }

  @GetMapping("/members/{memberId}")
  public EnvelopeResponse<GetTodoByDateResDto> getTodoByDateAndMemberId(
      @PathVariable Long memberId, @Valid GetTodoByDateReqDto getTodoByDateReqDto) {

    return EnvelopeResponse.<GetTodoByDateResDto>builder()
        .data(todoService.getMemberTodoByDate(memberId, getTodoByDateReqDto))
        .build();
  }

  @GetMapping("/members/{memberId}/monthly")
  public EnvelopeResponse<GetTodoMonthlyExistenceResDto> getTodoMonthlyExistence(
      @PathVariable Long memberId, @Valid GetTodoMonthlyExistenceReqDto getTodoMonthlyExistenceReqDto
      ) {

    return EnvelopeResponse.<GetTodoMonthlyExistenceResDto>builder()
        .data(todoService.getTodoMonthlyExistence(memberId, getTodoMonthlyExistenceReqDto))
        .build();
  }

  @GetMapping("/feed/following")
  public EnvelopeResponse<GetTodoFeedResDto> getFollowingFeed(
      @Valid GetTodoFeedByFollowingReqDto getTodoFeedByFollowingReqDto) {

    return EnvelopeResponse.<GetTodoFeedResDto>builder()
        .data(todoFeedService.getTodoFeedByFollowingTags(getTodoFeedByFollowingReqDto))
        .build();
  }

  @GetMapping("/feed")
  public EnvelopeResponse<GetTodoFeedResDto> getTagFeed(
      @Valid GetTodoFeedByTagReqDto getTodoFeedByTagReqDto) {

    return EnvelopeResponse.<GetTodoFeedResDto>builder()
        .data(todoFeedService.getTodoFeedByGivenTag(getTodoFeedByTagReqDto))
        .build();
  }

  @GetMapping("/{todoId}")
  public EnvelopeResponse<GetTodoDetailResDto> getTodoDetail(@PathVariable Long todoId) {

    return EnvelopeResponse.<GetTodoDetailResDto>builder()
        .data(todoFeedService.getTodoDetail(todoId))
        .build();
  }

  @PatchMapping("/{todoId}")
  public EnvelopeResponse<PatchTodoResDto> patchTodoAccomplishment(@PathVariable Long todoId) {

    return EnvelopeResponse.<PatchTodoResDto>builder()
        .data(todoService.patchTodoAccomplishment(todoId))
        .build();
  }

  @GetMapping("/{todoId}/personas")
  public EnvelopeResponse<GetTodoPersonaDetailsResDto> getTodoPersonas(@PathVariable Long todoId) {

    return EnvelopeResponse.<GetTodoPersonaDetailsResDto>builder()
        .data(todoPersonaService.getTodoPersonas(todoId))
        .build();
  }

  @PostMapping("/{todoId}/{todoPersonaId}")
  public EnvelopeResponse<PostPersonaReactResDto> postPersonaReaction(
      @PathVariable Long todoId,
      @PathVariable Long todoPersonaId) {

    return EnvelopeResponse.<PostPersonaReactResDto>builder()
        .data(todoPersonaService.postPersonaReaction(todoId, todoPersonaId))
        .build();
  }

}
