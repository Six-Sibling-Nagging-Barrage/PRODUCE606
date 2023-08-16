package com.a606.jansori.infra.redis.util;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingTodoUtil {

  private final RedisTemplate<String, String> redisTemplate;
  private static final String WAITING_TODO_IDENTIFIER = "waitingTodo:";

  public String findFrontTagId(Long tagId) {

    return redisTemplate.opsForList().leftPop(WAITING_TODO_IDENTIFIER + tagId);
  }

  public void popFront(Long tagId) {

    redisTemplate.opsForList().leftPop(WAITING_TODO_IDENTIFIER + tagId);
  }

  public void pushFrontAll(Long tagId, List<Long> todoIds) {

    redisTemplate.opsForList().leftPushAll(WAITING_TODO_IDENTIFIER + tagId,
        todoIds.stream().map(String::valueOf).collect(Collectors.toList()));
  }
}
