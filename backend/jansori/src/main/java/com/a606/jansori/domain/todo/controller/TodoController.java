package com.a606.jansori.domain.todo.controller;

import com.a606.jansori.domain.todo.dto.*;
import com.a606.jansori.domain.todo.service.TodoFeedService;
import com.a606.jansori.domain.todo.service.TodoService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private final TodoFeedService todoFeedService;

    @PostMapping
    public EnvelopeResponse postTodo(@Valid @RequestBody PostTodoReqDto postTodoReqDto, @RequestParam Long memberId) {

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
    public EnvelopeResponse<GetTodoFeedResDto> getFollowingFeed(Long memberId, Long cursor, Pageable pageable) {

        return EnvelopeResponse.<GetTodoFeedResDto>builder()
                .data(todoFeedService.getFollowingFeed(memberId, cursor, pageable))
                .build();
    }

}
