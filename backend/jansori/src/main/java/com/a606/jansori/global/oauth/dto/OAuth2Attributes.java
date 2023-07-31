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
public class OAuth2Attributes {
    private final Map<String, Object> attributes;
    private final String identifierName;

    public static OAuth2Attributes of(String provider, String identifierName, Map<String, Object> attributes) {
        if (provider.equals("google")) {
            return ofOAuthIdentifierAndAttributes(identifierName, attributes);
        } else {
            throw new OAuthProviderNotFoundException();
        }
    }

    private static OAuth2Attributes ofOAuthIdentifierAndAttributes(String identifierName,
                                                                   Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .attributes(attributes)
                .identifierName(identifierName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .oauthIdentifier((String) this.getAttributes().get(identifierName))
                .memberRole(MemberRole.ROLE_USER)
                .oauthType(OauthType.GOOGLE)
                .ticket(50L)
                .build();
    }

}
