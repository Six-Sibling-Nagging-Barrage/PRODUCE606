package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.dto.NagOfNagBox;
import com.a606.jansori.domain.tag.domain.Tag;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NagBoxTest {

  private static List<Nag> nags;

  @DisplayName("잔소리함의 TOP RANKING 을 멤버가 조회할때, 멤버가 초성을 해제했다면 unlocked 상태가 변한다.")
  @ParameterizedTest
  @MethodSource(value = "generateNagsWithNagUnlock")
  void Given_ValidMember_When_Get_NagBox_Then_Success(List<NagUnlock> nagUnlocks,
      int unlockedCount) {
    NagBox nagBox = new NagBox(nags, nagUnlocks);

    List<NagOfNagBox> nagBoxes = nagBox.getNags();

    //잔소리함 조회하는 멤버가 TOP 5 랭킹 잔소리를 볼때, 초성을 해제했다면 UNLOCKED 상태가 true 이다.
    // 테스트 메서드 파라미터로 들어오는 nagUnlocks 의 개수에 따라 nagBox.getNags()의 잠금 해제된 잔소리 개수가
    // nagBox.getNags()의 잠금 해제된 잔소리 개수가 같다.
    Assertions.assertThat(nagBoxes.stream().filter(NagOfNagBox::getUnlocked).count())
        .isEqualTo(unlockedCount);
  }

  static Stream<Arguments> generateNagsWithNagUnlock() {
    Member member = Member.builder()
        .id(1L)
        .nickname("taeyong")
        .build();
    Tag tag = new Tag(1L, "tag", 0);
    nags = List.of(new Nag(1L, "잔소리1", "ㅈㅅㄹ1", 1, tag, member, null),
        new Nag(2L, "잔소리2", "ㅈㅅㄹ2", 2, tag, member, null),
        new Nag(3L, "잔소리3", "ㅈㅅㄹ3", 3, tag, member, null),
        new Nag(4L, "잔소리4", "ㅈㅅㄹ4", 4, tag, member, null),
        new Nag(5L, "잔소리5", "ㅈㅅㄹ5", 5, tag, member, null));

    return Stream.of(
        Arguments.of(List.of(
            new NagUnlock(1L, member, nags.get(0)),
            new NagUnlock(2L, member, nags.get(1))), 2),

        Arguments.of(Collections.emptyList(), 0),

        Arguments.of(List.of(
            new NagUnlock(1L, member, nags.get(0)),
            new NagUnlock(2L, member, nags.get(1)),
            new NagUnlock(3L, member, nags.get(2))
        ), 3)
    );
  }
}