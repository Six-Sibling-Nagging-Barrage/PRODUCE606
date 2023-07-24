package com.a606.jansori.global.oauth;

import com.a606.jansori.domain.member.domain.Member;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        Member member = new Member();
        return member;
    });


    private final String registrationId;
    private final Function<Map<String, Object>, Member> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, Member> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static Member extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }

}
