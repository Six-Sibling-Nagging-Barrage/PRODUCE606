package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "nag_unlock")
public class NagUnlock {

  @Id
  @Column(name = "nag_unlock_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nag_id")
  private Nag nag;

  public static NagUnlock ofUnlockPreviewByNagAndMember(Nag nag, Member member) {
    return NagUnlock.builder()
        .nag(nag)
        .member(member)
        .build();
  }
}
