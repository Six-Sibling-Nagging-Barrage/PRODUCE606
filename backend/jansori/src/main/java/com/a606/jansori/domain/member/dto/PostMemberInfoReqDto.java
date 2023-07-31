package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostMemberInfoReqDto {

    private String nickname;
    private String bio;
    private String imageUrl;
    private List<Long> tags;

}
