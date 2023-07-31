package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.persona.domain.Line;
import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.persona.exception.LineNotFoundException;
import com.a606.jansori.domain.persona.repository.LineRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.repository.NagTagRepository;
import com.a606.jansori.global.util.RandomUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NagRandomGenerator {

  private final NagTagRepository nagTagRepository;
  private final NagRepository nagRepository;
  private final LineRepository lineRepository;
  private final RandomUtil randomUtil;
  private final int MAX_PAGE_SIZE = 5;

  /**
   * 각 페르소나의 잔소리들 중에 랜덤으로 잔소리를 가져온다.
   *
   * @param persona 인스턴스
   * @return Line
   * @author 김태용
   */
  public Line getRandomLineOfPersona(Persona persona) {
    Long count = lineRepository.countByPersona(persona);

    if (count == 0) {
      throw new LineNotFoundException();
    }

    return lineRepository.findLineByPersona(
            persona,
            PageRequest.of(randomUtil.generate(count), 1))
        .get(0);
  }

  /**
   * todo의 잔소리를 랜덤으로 가져온다. 해당 잔소리는 member가 쓴 잔소리가 아니고, todo에 포함된 해시태그의 잔소리 를 가져온다.
   *
   * @param member   인스턴스
   * @param todoTags todo가 가지고 있는 tags
   * @return Nag
   * @author 신현철
   */
  public Nag getRandomNagWithTags(Member member, List<TodoTag> todoTags) {

    List<Tag> tags = todoTags.stream().map(TodoTag::getTag).collect(Collectors.toList());

    Long count = nagTagRepository.countDistinctByNag_MemberNotAndTagIn(member, tags);

    int randomIndex = randomUtil.generate(count);

    return nagTagRepository.findByNag_MemberNotAndTagIn(member, tags,
        PageRequest.of(randomIndex, 1)).get(0).getNag();
  }

  /**
   * 메인페이지에서 잔소리 좋아요 수의 TOP RANKING 5를 제외한 랜덤 잔소리들을 가져온다. 랜덤으로 선택된 페이지가 0일 경우 TOP RANKING 이기 때문에
   * random Index를 + 1을 해줍니다.
   *
   * @return 메인페이지에서 사용될 잔소리 리스트
   * @author 김태용
   */
  public List<Nag> getRandomNagsOfMainPage() {
    long count = (nagRepository.count() / MAX_PAGE_SIZE);

    int randomIndex = randomUtil.generate(count);
    randomIndex = randomIndex == 0 ? randomIndex + 1 : randomIndex;

    return nagRepository.findByRandomNagsNotInTopRank(
        PageRequest.of(randomIndex, MAX_PAGE_SIZE));
  }
}
