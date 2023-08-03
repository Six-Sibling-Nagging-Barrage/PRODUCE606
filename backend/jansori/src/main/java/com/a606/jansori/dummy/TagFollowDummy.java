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

  public List<TagFollow> createTagFollows(List<Tag> tags, List<Member> members) {
    int start = 0, end = start + 100;

    for (Member member : members) {
      for (int i = start; i < end; i++) {
        tagFollows.add(TagFollow.builder().tag(tags.get(i)).member(member).build());
      }
      start = end;
      if (start >= 1000) {
        start = 0;
      }
      end = start + 100;
    }

    return tagFollowRepository.saveAll(tagFollows);
  }

}
