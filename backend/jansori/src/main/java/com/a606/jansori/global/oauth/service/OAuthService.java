package com.a606.jansori.global.oauth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRepository;
import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.domain.OauthType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

// 로그인 성공 시 DB에 저장하는 서비스 로직
@Service
// 자동으로 의존성 주입(final이 붙은 것)
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("here");
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스에서 가져온 유저 정보를 담고 있음

        // 뭔지 부검해보자
//        System.out.println(oAth2User);
        System.out.println("delegate: " + delegate.toString());

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // OAuth 서비스 이름 (google)
        System.out.println("userRequest: " + userRequest.toString());

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth 로그인 시 키(PK)가 되는 값

        System.out.println("userNameAttributeName: " + userNameAttributeName); // sub
        System.out.println("registrationId: " + registrationId); // google
        System.out.println("oAuth2User.getAttributes(): " + oAuth2User.getAttributes().get("sub"));

        OAuth2Attributes oAuthAttributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Map<String, Object> memberAttribute = oAuthAttributes.toMap();

        Optional<Member> member = memberRepository.findByOauthIdentifier(oAuthAttributes.getUserNameAttributeName());

        if (member.isEmpty()) {
            System.out.println("here");
            Member newMember = Member.builder()
                    .oauthIdentifier((String) oAuth2User.getAttributes().get("sub"))
                    .memberRole(MemberRole.USER)
                    .oauthType(OauthType.GOOGLE)
                    .ticket(50L)
                    .build();
            memberRepository.save(newMember);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                memberAttribute, userNameAttributeName);


    }


}
