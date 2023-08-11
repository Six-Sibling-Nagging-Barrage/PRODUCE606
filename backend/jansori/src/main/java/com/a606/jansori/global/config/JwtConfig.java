package com.a606.jansori.global.config;

import com.a606.jansori.global.config.property.JwtConfigProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtConfigProperty.class)
public class JwtConfig {

}
