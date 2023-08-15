package com.a606.jansori.dummy;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "dummy")
@ConstructorBinding
@Profile("local")
public class DummyProperty {

  private final List<TodoDummy> todoDummies;
  private final List<String> tags;
  private final List<String> nicknames;
  private final List<String> profiles;

  @Getter
  @RequiredArgsConstructor
  @ConstructorBinding
  public static class TodoDummy{
    private final String content;
    private final List<String> tags;
  }
}
