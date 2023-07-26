package com.a606.jansori.domain.todo.controller;

import com.a606.jansori.domain.todo.dto.GetTodoListResDto;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.todo.service.TodoService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

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

}
