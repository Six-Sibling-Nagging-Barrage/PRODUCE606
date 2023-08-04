package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.tag.domain.NagTag;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.repository.NagTagRepository;
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
  private final NagTagRepository nagTagRepository;

  private final List<Nag> nags = new ArrayList<>();
  private final List<NagTag> nagTags = new ArrayList<>();

  public List<Nag> createNags(List<Member> members) {
    int start = 0;

    for (int i = 0; i < members.size(); i++) {
      for (int j = 0; j < 20; j++) {
        nags.add(
            Nag.builder().member(members.get(i)).content("잔소리" + start++).preview("ㅈㅅㄹ" + start)
                .likeCount(i).build());
      }
    }

    return nagRepository.saveAll(nags);
  }

  public void createNagTags(List<Nag> nags, List<Tag> tags) {
    int start = 0;
    for (Nag nag : nags) {
      nagTags.add(NagTag.of(nag, tags.get(start++)));

      if (start >= tags.size()) {
        start = 0;
      }
    }
    nagTagRepository.saveAll(nagTags);
  }
}
