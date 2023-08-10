package com.a606.jansori.infra.redis.util;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlackListUtil {

  private final RedisTemplate<String, Object> redisTemplate;

  private final RedisTemplate<String, Object> blackListTemplate;

  public void set(String key, Object o, long expiration) {
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
    redisTemplate.opsForValue().set(key, o, expiration, TimeUnit.MILLISECONDS);
  }

  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public boolean delete(String key) {
    return Boolean.TRUE.equals(redisTemplate.delete(key));
  }

  public boolean hasKey(String key) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }

  public void setBlackList(String key, Object o, long expiration) {
    blackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
    blackListTemplate.opsForValue().set(key, o, expiration, TimeUnit.MILLISECONDS);
  }

  public Object getBlackList(String key) {
    return blackListTemplate.opsForValue().get(key);
  }

  public boolean deleteBlackList(String key) {
    return Boolean.TRUE.equals(blackListTemplate.delete(key));
  }

  public boolean isBlackList(String key) {

    return Boolean.TRUE.equals(blackListTemplate.hasKey(key));
  }
}
