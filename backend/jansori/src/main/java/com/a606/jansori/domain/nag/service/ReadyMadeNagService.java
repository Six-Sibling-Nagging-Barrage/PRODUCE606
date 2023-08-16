package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.todo.repository.WaitingTodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadyMadeNagService {

  private final WaitingTodoRepository waitingTodoRepository;



}
