package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagLike;
import com.a606.jansori.domain.nag.repository.NagLikeRepository;
import com.a606.jansori.domain.nag.repository.NagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class NagLikeDummy {

  private final NagLikeRepository nagLikeRepository;

  public List<NagLike> createNagLikes(List<Member> members, List<Nag> nags) {

    List<NagLike> nagLikes = new ArrayList<>();

    int x = 0;

    for (int i = 0 ; i < members.size(); i++) {

      for (int j = 0; j < 10; j++) {
        x = x + 53;
        x %= nags.size();

        nagLikes.add(nagLikeRepository.save(NagLike.builder()
            .member(members.get(i))
            .nag(nags.get(x))
            .build()));
      }
    }

    return nagLikes;
  }
}
