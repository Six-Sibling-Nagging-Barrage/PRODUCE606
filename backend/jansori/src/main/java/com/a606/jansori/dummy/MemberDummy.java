package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.domain.MemberStatus;
import com.a606.jansori.domain.member.domain.OauthType;
import com.a606.jansori.domain.member.repository.MemberRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class MemberDummy {

  private final MemberRepository memberRepository;

  public List<Member> createMembers() {

    List<Member> members = new ArrayList<>();

    for (int i = 0; i < 50; i++) {

      Member member = Member.builder()
          .memberRole(MemberRole.USER)
          .memberState(MemberStatus.ACTIVE)
          .bio("bio" + i)
          .imageUrl("www.image" + i + ".png")
          .nickname("nickname" + i)
          .oauthIdentifier("oauth" + i)
          .oauthType(OauthType.GOOGLE)
          .build();

      members.add(memberRepository.save(member));

      memberRepository.updateDate(member, LocalDateTime.now().minusDays(i));
    }

    return members;
  }

}
