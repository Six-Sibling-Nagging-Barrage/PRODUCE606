package com.a606.jansori.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostNicknameResDto {
    Boolean isPossible;

    public static PostNicknameResDto of(Boolean isPossible){
        return PostNicknameResDto.builder()
                .isPossible(isPossible)
                .build();
    }
}
