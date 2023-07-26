package com.a606.jansori.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetUserProfileResDto {

    private Long id;

    private String nickname;

    private String bio;

    private String imageUrl;

    private Long ticket;

}
