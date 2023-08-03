package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class MemberDummy {

  private final MemberRepository memberRepository;

  

}
