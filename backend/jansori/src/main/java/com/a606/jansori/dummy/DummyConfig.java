package com.a606.jansori.dummy;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableConfigurationProperties(DummyProperty.class)
@Profile("local")
public class DummyConfig {
}
