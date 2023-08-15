package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.nag.util.PreviewUtil;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.dummy.DummyProperty.NagDummy;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class NagWithNagTagDummy {

  private final DummyProperty dummyProperty;
  private final NagRepository nagRepository;
  private final PreviewUtil previewUtil;

  public List<Nag> createNags(List<Member> members, List<Tag> tags) {

    List<Nag> nags = new ArrayList<>();

    List<NagDummy> nagDummies = dummyProperty.getNagDummies();
    int memberSize = members.size();

    for (NagDummy nagDummy : nagDummies) {
      int randMemberIdx = (int) (Math.random() * memberSize);
      nags.add(Nag.builder()
          .member(members.get(randMemberIdx))
          .tag(tags.stream().filter((tag) -> tag.getName().equals(nagDummy.getTag())).findFirst()
              .orElse(null))
          .preview(previewUtil.convertNagToPreview(nagDummy.getContent()))
          .content(nagDummy.getContent())
          .likeCount((int) (Math.random() * 1024))
          .build());
    }

    return nagRepository.saveAll(nags);
  }
}
