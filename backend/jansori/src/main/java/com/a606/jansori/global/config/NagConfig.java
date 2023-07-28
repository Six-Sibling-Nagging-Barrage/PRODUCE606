package com.a606.jansori.global.config;

import com.a606.jansori.domain.tag.service.NagRandomGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NagConfig {

  @Bean
  public NagRandomGenerator tagRandomGenerator() {
    return new NagRandomGenerator();
  }
}
