package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
public class GetUserProfileResDto {

    private Long id;

    private String nickname;

    private String bio;

    private String imageUrl;

    private Long ticket;

    public static GetUserProfileResDto from(Member member) {
        return GetUserProfileResDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .bio(member.getBio())
                .imageUrl(member.getImageUrl())
                .ticket(member.getTicket())
                .build();
    }
}
