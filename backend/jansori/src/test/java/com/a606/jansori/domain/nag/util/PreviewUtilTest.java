package com.a606.jansori.domain.nag.util;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PreviewUtilTest {

  @ParameterizedTest
  @MethodSource("generateNagsWithPreview")
  void Given_Nags_When_MakePreview_Then_Success(String nag, String preview) {
    PreviewUtil previewUtil = new PreviewUtil();
    Assertions.assertThat(previewUtil.convertNagToPreview(nag)).isEqualTo(preview);
  }

  static Stream<Arguments> generateNagsWithPreview() {
    return Stream.of(
        Arguments.of("?!@#@!_+~!/*", "?!@#@!_+~!/*"),
        Arguments.of("ZZZZZZ", "______"),
        Arguments.of("Hello World!", "_e__o _o___!"),
        Arguments.of("11214!)@", "11214!)@"),
        Arguments.of("태용아, Hold Up!", "ㅌㅇㅇ, _o__ U_!"),
        Arguments.of("태용아, 움직여야겠지?", "ㅌㅇㅇ, ㅇㅈㅇㅇㄱㅈ?")
    );
  }
}