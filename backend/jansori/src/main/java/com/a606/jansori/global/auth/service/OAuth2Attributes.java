package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.domain.OauthType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String userNameAttributeName;

    static OAuth2Attributes of(String provider, String userNameAttributeName, Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(userNameAttributeName, attributes);
            default:
                throw new IllegalStateException();
        }
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuth2Attributes.builder()
                .attributes(attributes)
                .userNameAttributeName(userNameAttributeName)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .oauthIdentifier((String) this.getAttributes().get("sub"))
                .memberRole(MemberRole.USER)
                .oauthType(OauthType.GOOGLE)
                .ticket(50L)
                .build();
    }

}
