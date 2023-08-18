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

  private static final String BLACKLIST_IDENTIFIER = "blacklist:";

  public void setBlackList(String key, Object o, long expiration) {
    blackListTemplate.opsForValue().set(BLACKLIST_IDENTIFIER + key, o, expiration, TimeUnit.MILLISECONDS);
  }

  public boolean isBlackList(String key) {

    return Boolean.TRUE.equals(blackListTemplate.hasKey(BLACKLIST_IDENTIFIER + key));
  }
}
