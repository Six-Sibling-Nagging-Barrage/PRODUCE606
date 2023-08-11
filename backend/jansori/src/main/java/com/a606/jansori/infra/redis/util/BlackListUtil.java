package com.a606.jansori.infra.redis.util;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlackListUtil {

  private final RedisTemplate<String, Object> blackListTemplate;

  public void setBlackList(String key, Object o, long expiration) {
    blackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
    blackListTemplate.opsForValue().set(key, o, expiration, TimeUnit.MILLISECONDS);
  }

  public boolean isBlackList(String key) {

    return Boolean.TRUE.equals(blackListTemplate.hasKey(key));
  }
}
