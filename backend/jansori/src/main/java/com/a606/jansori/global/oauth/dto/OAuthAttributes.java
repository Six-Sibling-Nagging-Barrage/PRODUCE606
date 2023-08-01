package com.a606.jansori.global.oauth.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.domain.OauthType;
import com.a606.jansori.domain.member.exception.OAuthProviderNotFoundException;
import com.a606.jansori.global.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String oAuthIdentifier;

    public static OAuthAttributes of(String provider, String oAuthIdentifier, Map<String, Object> attributes) {
        if (provider.equals("google")) {
            return ofOAuthIdentifierAndAttributes(oAuthIdentifier, attributes);
        } else {
            throw new OAuthProviderNotFoundException();
        }
    }

    private static OAuthAttributes ofOAuthIdentifierAndAttributes(String oAuthIdentifier,
                                                                   Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .attributes(attributes)
                .oAuthIdentifier(oAuthIdentifier)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .oauthIdentifier((String) this.getAttributes().get(oAuthIdentifier))
                .memberRole(MemberRole.GUEST)
                .oauthType(OauthType.GOOGLE)
                .ticket(50L)
                .build();
    }

}
