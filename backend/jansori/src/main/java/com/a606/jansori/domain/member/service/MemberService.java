package com.a606.jansori.domain.member.service;

import com.a606.jansori.domain.member.dto.PostNicknameReqDto;
import com.a606.jansori.domain.member.dto.PostNicknameResDto;
import com.a606.jansori.domain.member.exception.DuplicatedNicknameException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public PostNicknameResDto checkNicknameIsAvailable(PostNicknameReqDto postNicknameReqDto){

        Boolean isExist = memberRepository.existsByNickname(postNicknameReqDto.getNickname());
        System.out.println(isExist);
        if(isExist){
            throw new DuplicatedNicknameException();
        } else {
            return PostNicknameResDto.of(true);
        }
    }
}