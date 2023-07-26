package com.a606.jansori.global.oauth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;

// 로그인 성공 시 DB에 저장하는 서비스 로직
@Service
// 자동으로 의존성 주입(final이 붙은 것)
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스에서 가져온 유저 정보를 담고 있음
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // OAuth 서비스 이름 (google)
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth 로그인 시 키(PK)가 되는 값

        OAuth2Attributes oAuthAttributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        System.out.println("(String) oAuth2User.getAttributes().get(sub): " + (String) oAuth2User.getAttributes().get("sub"));
        Optional<Member> member = memberRepository.findByOauthIdentifier((String) oAuth2User.getAttributes().get("sub"));
        System.out.println("member.toString(): " + member.toString());
        System.out.println("member.isEmpty(): " + member.isEmpty());

        if (member.isEmpty()) {
            Member newMember = oAuthAttributes.toEntity();
            memberRepository.save(newMember);
            httpSession.setAttribute("member", new SessionMember(newMember));
        } else {
            Member newMember = member.get();
            httpSession.setAttribute("member", new SessionMember(newMember));
        }

        System.out.println(httpSession.toString());


        Enumeration<String> enumeration = httpSession.getAttributeNames();
        while(enumeration.hasMoreElements()){

            String sessionName = enumeration.nextElement().toString();

            String sessionValue= httpSession.getAttribute(sessionName).toString();

            System.out.println("Name: "+ sessionName+ "<br/>");

            System.out.println("Value: "+ sessionValue+ "<br/>");

        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuthAttributes.getAttributes(), userNameAttributeName);
    }

}
//        Map<String, Object> memberAttribute = oAuthAttributes.toMap();

//        Member member = memberRepository.findByOauthIdentifier(oAuthAttributes.getUserNameAttributeName()).orElse(oAuthAttributes.toEntity());

//        Member member = saveOrUpdate(oAuthAttributes);
//        httpSession.setAttribute("member", new SessionMember(member));

//        Optional<Member> member = memberRepository.findByOauthIdentifier(oAuthAttributes.getUserNameAttributeName());
//
//        if (member.isEmpty()) {
//            System.out.println("here");
//            Member newMember = Member.builder()
//                    .oauthIdentifier((String) oAuth2User.getAttributes().get("sub"))
//                    .memberRole(MemberRole.USER)
//                    .oauthType(OauthType.GOOGLE)
//                    .ticket(50L)
//                    .build();
//            memberRepository.save(newMember);
//        }


//    private Member saveOrUpdate(OAuth2Attributes attributes){
//        Member member = memberRepository.findByOauthIdentifier(attributes.getUserNameAttributeName())
//                .orElse(attributes.toEntity());
//        return memberRepository.save(member);
//    }

//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                memberAttribute, userNameAttributeName);