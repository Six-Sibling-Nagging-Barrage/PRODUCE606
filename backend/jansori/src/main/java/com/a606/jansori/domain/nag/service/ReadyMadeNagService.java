package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.exception.TodoNotFoundException;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.event.NagDeliveryEvent;
import com.a606.jansori.infra.redis.util.WaitingTodoUtil;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReadyMadeNagService {

  private final WaitingTodoUtil waitingTodoUtil;
  private final TodoRepository todoRepository;
  private final ApplicationEventPublisher publisher;
  private final Clock clock;

  @Transactional
  public void deliverNagToWaitingTodoQueue(Nag nag) {

    Long tagId = nag.getTag().getId();

    LocalDate currentDate = LocalDate.now(clock).minusDays(1);

    List<Long> todoIds = new ArrayList<>();

    while (true) {

      String front = waitingTodoUtil.findFrontTagId(tagId);

      if (front == null) {
        break;
      }

      Long todoId = Long.valueOf(front);

      Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

      if (todo.getMember().equals(nag.getMember())) {
        todoIds.add(todoId);
        continue;
      }

      if (todo.getTodoAt().isBefore(currentDate) || todo.getFinished() || todo.getNag() != null) {
        waitingTodoUtil.popFront(tagId);
        continue;
      }

      todo.setNag(nag);

      publisher.publishEvent(new NagDeliveryEvent(todoRepository.save(todo)));
      break;

    }

    if (todoIds.isEmpty()) {
      return;
    }

    waitingTodoUtil.pushFrontAll(tagId, todoIds);
  }

}
