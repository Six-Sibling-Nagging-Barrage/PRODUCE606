package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.dto.TagDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class GetMyProfileResDto extends GetMemberProfileResDto{

  private Long ticket;

  public static GetMyProfileResDto from(Member member) {
    return GetMyProfileResDto.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .bio(member.getBio())
        .imageUrl(member.getImageUrl())
        .ticket(member.getTicket())
        .build();
  }
}
