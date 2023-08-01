package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;

import com.a606.jansori.domain.member.repository.MemberRepository;


import com.a606.jansori.global.auth.dto.OAuthAttributes;
import com.a606.jansori.global.auth.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String oAuthIdentifier = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        String identifier = (String) oAuth2User.getAttributes().get(oAuthIdentifier);

        OAuthAttributes oAuthAttributes = OAuthAttributes
                .getAttributesByProvider(registrationId, oAuthIdentifier, oAuth2User.getAttributes());
        Member member = memberRepository.findByOauthIdentifier(identifier).
                orElseGet(() -> memberRepository.save(oAuthAttributes.toEntity()));

        return new PrincipalDetails(oAuth2User.getAttributes(), member.getId(), member.getMemberRole());
    }

}
