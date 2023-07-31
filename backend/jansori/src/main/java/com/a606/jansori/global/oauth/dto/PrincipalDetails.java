package com.a606.jansori.global.oauth.dto;

import com.a606.jansori.domain.member.domain.MemberRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private static final long serialVersionUID = 1L;
    private final Long id;
    private final MemberRole role;
    private final Map<String, Object> attributes;

    public PrincipalDetails(Map<String, Object> attributes, Long id, MemberRole role) {
        this.id = id;
        this.role = role;
        this.attributes = attributes;
    }

    public Long getId() {
        return this.id;
    }

    public MemberRole getRole() {
        return this.role;
    }

    @Override
    public String getName() {
        return (String) this.attributes.get("name");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
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
        return null;
    }

    @Override
    public String getUsername() {
        return (String) this.attributes.get("name");
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
