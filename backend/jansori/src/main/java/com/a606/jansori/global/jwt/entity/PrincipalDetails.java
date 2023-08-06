package com.a606.jansori.global.jwt.entity;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {
//    private static final long serialVersionUID = 1L;
    private Long id;
    private MemberRole role;
    private Member member;

    public PrincipalDetails(Member member) {
        this.id = member.getId();
        this.role = member.getMemberRole();
        this.member = member;
    }

    public Long getId() {
        return this.id;
    }

    public MemberRole getRole() {
        return this.role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return String.valueOf(role);
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
