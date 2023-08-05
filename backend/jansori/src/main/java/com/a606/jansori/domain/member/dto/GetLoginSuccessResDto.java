package com.a606.jansori.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLoginSuccessResDto {

    private Long id;
    private boolean isRegistered;

    public GetLoginSuccessResDto of(Long id, boolean isRegistered) {
        return GetLoginSuccessResDto.builder()
                .id(id)
                .isRegistered(isRegistered)
                .build();
    }

}
