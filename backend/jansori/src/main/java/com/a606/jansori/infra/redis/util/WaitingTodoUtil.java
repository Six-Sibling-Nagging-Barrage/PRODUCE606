package com.a606.jansori.infra.redis.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingTodoUtil {

  private final RedisTemplate<String, String> redisTemplate;
  private static final String WAITING_TODO_IDENTIFIER = "waitingTodo:";

  public String findFrontTodoId(Long tagId) {

    return redisTemplate.opsForList().leftPop(String.valueOf(tagId));
  }

  public void popFront(Long tagId, Long todoId) {

    redisTemplate.opsForList().leftPop(String.valueOf(tagId));
  }

  public void pushRear(Long tagId, Long todoId) {

    redisTemplate.opsForList()
        .rightPush(WAITING_TODO_IDENTIFIER + String.valueOf(tagId), String.valueOf(todoId));
  }
}
