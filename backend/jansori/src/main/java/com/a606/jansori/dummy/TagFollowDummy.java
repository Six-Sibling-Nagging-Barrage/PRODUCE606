package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TagFollowDummy {

  private final TagFollowRepository tagFollowRepository;

  private final List<TagFollow> tagFollows = new ArrayList<>();

  public void createTagFollows(List<Tag> tags, List<Member> members) {
    for (Member member : members) {
      for (Tag tag : tags) {
        tagFollows.add(TagFollow.builder().tag(tag).member(member).build());
      }
    }

    tagFollowRepository.saveAll(tagFollows);
  }

}
