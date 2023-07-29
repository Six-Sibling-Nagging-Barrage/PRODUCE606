package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.global.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "nag")
public class Nag extends BaseTimeEntity {

  @Id
  @Column(name = "nag_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @Column
  private String preview;

  @Column(name = "like_count")
  @Builder.Default
  private Integer likeCount = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public static Nag of(Member member, PostNagReqDto postNagReqDto, String preview) {
    return Nag.builder()
        .member(member)
        .preview(preview)
        .content(postNagReqDto.getContent())
        .build();
  }
}
