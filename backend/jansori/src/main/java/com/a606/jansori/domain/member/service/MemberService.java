package com.a606.jansori.domain.member.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.a606.jansori.domain.member.dto.PostNicknameReqDto;
import com.a606.jansori.domain.member.dto.PostNicknameResDto;
import com.a606.jansori.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public PostNicknameResDto checkNicknameAvailable(PostNicknameReqDto postNicknameReqDto){
        boolean isExist = memberRepository.duplicateCheckByNickname(postNicknameReqDto.getNickname());

        if(isExist){
            System.out.println("아직 예외처리 안 만들어서 일단 이렇게 함");
            return PostNicknameResDto.of(false);
        } else {
            return PostNicknameResDto.of(true);
        }
    }
}
