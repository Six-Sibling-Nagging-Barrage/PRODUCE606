package com.a606.jansori.global.auth.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
public class PrincipalDetails implements UserDetails {

  private Member member;

  public PrincipalDetails(Member member) {
    this.member = member;
  }

  public Long getId() {
    return this.member.getId();
  }

  public MemberRole getRole() {
    return this.member.getMemberRole();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collection = new ArrayList<>();
    collection.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {

        return String.valueOf(member.getMemberRole());
      }
    });
    return collection;
  }

  @Override
  public String getPassword() {
    return this.member.getPassword();
  }

  @Override
  public String getUsername() {
    return (String) this.member.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
