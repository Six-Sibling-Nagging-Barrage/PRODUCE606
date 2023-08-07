package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.domain.MemberStatus;
import com.a606.jansori.domain.member.domain.OauthType;
import com.a606.jansori.domain.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class MemberDummy {

  private final MemberRepository memberRepository;

  public List<Member> createMembers() {
    int targetMemberSize = 20;
    List<Member> members = new ArrayList<>();

    for (int i = 0; i < targetMemberSize; i++) {

      Member member = Member.builder()
          .email("email"+i+"@ssafy.com")
          .password("$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6")
          .memberRole(MemberRole.USER)
          .memberState(MemberStatus.ACTIVE)
          .bio("bio" + i)
          .imageUrl("www.image" + i + ".png")
          .nickname("nickname" + i)
          .build();

      members.add(memberRepository.save(member));

      memberRepository.updateDate(member, LocalDateTime.now().minusDays(i));
    }

    return members;
  }

}
