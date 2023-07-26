package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedMemberDto {

    private Long memberId;

    private String nickname;

    private String imageUrl;

    public static FeedMemberDto from(Member member) {

        return FeedMemberDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .build();
    }

}
