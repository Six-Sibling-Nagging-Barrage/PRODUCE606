package com.a606.jansori.infra.redis.util;

import com.a606.jansori.global.auth.domain.RefreshToken;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenUtil {

  private final RedisTemplate<String, Object> redisTemplate;

  public void save(RefreshToken refreshToken, Long expire) {

    redisTemplate.setValueSerializer(
        new Jackson2JsonRedisSerializer<>(refreshToken.getRefreshToken().getClass()));

    redisTemplate.opsForValue().set(refreshToken.getEmail(), refreshToken.getRefreshToken());
    redisTemplate.expire(refreshToken.getRefreshToken(), expire, TimeUnit.MILLISECONDS);
  }

  public Optional<RefreshToken> findByEmail(String email) {

    String refreshToken = (String) redisTemplate.opsForValue().get(email);

    if (Objects.isNull(refreshToken)) {
      return Optional.empty();
    }

    return Optional.of(new RefreshToken(email, refreshToken));
  }

  public boolean deleteByEmail(String email) {
    return Boolean.TRUE.equals(redisTemplate.delete(email));
  }
}
