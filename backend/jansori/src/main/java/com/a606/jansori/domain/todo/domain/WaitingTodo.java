package com.a606.jansori.domain.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "waitingTodo", timeToLive = 86400)
public class WaitingTodo {

  @Id
  private String tagId;

  private String todoId;

}
