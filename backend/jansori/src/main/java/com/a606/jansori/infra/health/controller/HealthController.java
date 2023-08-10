package com.a606.jansori.infra.health.controller;

import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

  private final RedisTemplate<String, Object> redisTemplate;

  @GetMapping
  public String checkHealth() {
    return "ok";
  }

  @GetMapping("/redis")
  public EnvelopeResponse checkRedis() {

    try {
      redisTemplate.getConnectionFactory().getConnection().ping();

      return EnvelopeResponse.<String>builder()
          .data("Redis Connection Success")
          .build();

    } catch (Exception e) {

      return EnvelopeResponse.<String>builder()
          .data("Redis Connection Failed")
          .build();
    }
  }
}
