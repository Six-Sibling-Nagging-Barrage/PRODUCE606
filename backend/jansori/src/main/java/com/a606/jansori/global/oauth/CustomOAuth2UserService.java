package com.a606.jansori.global.oauth;

import com.a606.jansori.domain.member.domain.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService extends OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private MemberRepository memberRepository;
    private
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        //String username = provider + "_" + providerId; // 사용자가 입력한 값은 아니지만 임의로 만들어줌

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("패스워드"+uuid); // 비밀번호도 임의로 만들어줌

        String email = oAuth2User.getAttribute("email");
        Role role = Role.ROLE_USER; // 어디서 가져오지?

        User byEmail = userRepository.findByEmail(email); // username으로 가져오면 안되겠다.

        if(byEmail == null){
            byEmail = User.oauth2Register()
                    .email(email).password(password).role(role)
                    .provider(provider).providerId(providerId)
                    .build();
            userRepository.save(byEmail);
        }

        return new PrincipalDetails(byEmail, oAuth2User.getAttributes());



    }



}