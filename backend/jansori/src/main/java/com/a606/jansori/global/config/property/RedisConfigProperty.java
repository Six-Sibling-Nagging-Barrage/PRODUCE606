package com.a606.jansori.global.config.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.redis")
@ConstructorBinding
public class RedisConfigProperty {

  private final String host;

  private final int port;
}
