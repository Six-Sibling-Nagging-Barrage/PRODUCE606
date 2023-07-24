package com.a606.jansori.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostNicknameResDto {

    boolean isPossible;

    public static PostNicknameResDto of(boolean isPossible){
        return PostNicknameResDto.builder()
                .isPossible(isPossible)
                .build();
    }
}
