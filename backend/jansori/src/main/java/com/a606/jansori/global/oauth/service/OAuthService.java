package com.a606.jansori.global.oauth.service;

import com.a606.jansori.domain.member.domain.Member;

import com.a606.jansori.domain.member.repository.MemberRepository;

import com.a606.jansori.global.oauth.dto.OAuth2Attributes;
import com.a606.jansori.global.oauth.dto.PrincipalDetails;

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

        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스에서 가져온 유저 정보를 담고 있음

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // OAuth 서비스 이름 (google)

        String identifierName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth 로그인 시 키(PK)가 되는 값

        String identifier = (String) oAuth2User.getAttributes().get(identifierName);

        OAuth2Attributes oAuthAttributes = OAuth2Attributes
                .of(registrationId, identifierName, oAuth2User.getAttributes());
        Optional<Member> memberOptional = memberRepository.findByOauthIdentifier(identifier);
        Member member = saveOrGetMember(memberOptional, oAuthAttributes);

        return new PrincipalDetails(oAuth2User.getAttributes(), member.getId(), member.getMemberRole());
    }

    private Member saveOrGetMember(Optional<Member> member, OAuth2Attributes oAuth2Attributes) {
        if (member.isEmpty()) {
            Member newMember = oAuth2Attributes.toEntity();
            memberRepository.save(newMember);
            return newMember;
        }
        return member.get();
    }


}
