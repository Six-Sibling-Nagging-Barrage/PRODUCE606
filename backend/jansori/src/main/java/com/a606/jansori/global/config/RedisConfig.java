package com.a606.jansori.global.config;

import com.a606.jansori.global.config.property.RedisConfigProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableConfigurationProperties(RedisConfigProperty.class)
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisConfigProperty property;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(property.getHost(), property.getPort());
  }

}
