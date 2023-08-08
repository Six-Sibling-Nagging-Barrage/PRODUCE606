package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class NagWithNagTagDummy {

  private final NagRepository nagRepository;
  private final List<Nag> nags = new ArrayList<>();

  public List<Nag> createNags(List<Member> members, List<Tag> tags) {
    int start;

    for (int i = 0; i < members.size(); i++) {
      start = 0;
      for (int j = 0; j < 20; j++) {
        nags.add(
            Nag.builder()
                .member(members.get(i))
                .tag(tags.get(start))
                .preview("ㅈㅅㄹ" + start)
                .content("잔소리" + start++)
                .likeCount(i).build());
      }
    }

    return nagRepository.saveAll(nags);
  }
}
