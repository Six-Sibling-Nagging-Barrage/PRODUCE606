package com.a606.jansori.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetDuplicateNicknameResDto {
    Boolean isPossible;

    public static GetDuplicateNicknameResDto from(Boolean isPossible){
        return GetDuplicateNicknameResDto.builder()
                .isPossible(isPossible)
                .build();
    }
}
