package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.auth.entity.PrincipalDetails;
import com.a606.jansori.global.auth.exception.AuthMemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    return memberRepository.findMemberByEmail(email)
        .map(this::createUserDetails)
        .orElseThrow(() -> new AuthMemberNotFoundException());

  }

  private UserDetails createUserDetails(Member member) {

    GrantedAuthority grantedAuthority =
        new SimpleGrantedAuthority(member.getMemberRole().toString());

    return new PrincipalDetails(member);

  }
}
