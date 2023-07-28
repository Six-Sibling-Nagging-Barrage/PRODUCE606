package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.persona.domain.Line;
import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.repository.NagTagRepository;
import com.a606.jansori.global.util.RandomUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NagRandomGenerator {

  private final NagTagRepository nagTagRepository;

  private final RandomUtil randomUtil;

  public Line getRandomLineOfPersona(Member member, Persona persona) {
    return null;
  }

  public Nag getRandomNagWithTags(Member member, List<TodoTag> todoTags) {

    List<Tag> tags = todoTags.stream().map(TodoTag::getTag).collect(Collectors.toList());

    Long count = nagTagRepository.countDistinctByNag_MemberNotAndTagIn(member, tags);

    int randomIndex = randomUtil.generate(count);

    return nagTagRepository.findByNag_MemberNotAndTagIn(member,
            tags,
            PageRequest.of(randomIndex, 1))
        .get(0)
        .getNag();
  }
}