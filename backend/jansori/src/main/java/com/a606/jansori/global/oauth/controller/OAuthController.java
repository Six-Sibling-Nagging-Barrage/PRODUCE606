package com.a606.jansori.global.oauth.controller;

import com.a606.jansori.global.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private final HttpSession httpSession;
    @GetMapping("/loginInfo")
    public String oauthLoginInfo(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

//        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
////Request 가지고 오기
//        HttpServletRequest httpRequest = servletRequestAttribute.getRequest();
////Session 가지고 오기
//        HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);

        System.out.println("here: ~~" + httpSession.getAttributeNames());
        System.out.println("here: ~~" + httpSession.getAttribute("member"));
//        System.out.println("httpSession.getServletContext().toString(): ~~" + httpSession);
        httpSession.setAttribute("test", "test");
        System.out.println(httpSession.getAttribute("test"));
        Enumeration enumeration= httpSession.getAttributeNames();

        while(enumeration.hasMoreElements()){

            String sessionName = enumeration.nextElement().toString();

            String sessionValue= httpSession.getAttribute(sessionName).toString();

            System.out.println("Name: "+ sessionName+ "<br/>");

            System.out.println("Value: "+ sessionValue+ "<br/>");

        }




        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        return attributes.toString();
    }
}
