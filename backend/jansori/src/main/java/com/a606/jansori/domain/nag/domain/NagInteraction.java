package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.global.event.NagLikeEvent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "nag_interaction")
@Table(indexes = {
    @Index(name = "idx_unique_nag_member", columnList = "nag_id, member_id", unique = true)
}
)
public class NagInteraction {

  @Id
  @Column(name = "nag_interaction_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nag_id")
  private Nag nag;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column
  @Builder.Default
  private Boolean nagLike = false;

  @Column
  @Builder.Default
  private Boolean nagUnlock = true;

  public void toggleNagLike(Nag nag, ApplicationEventPublisher publisher) {
    if (nagLike) {
      nag.decreaseLikeCount();
    } else {
      publisher.publishEvent(new NagLikeEvent(member, nag));
      nag.increaseLikeCount();
    }
    nagLike = !nagLike;
  }

  public static NagInteraction ofUnlockPreviewByNagAndMember(Nag nag, Member member) {
    return NagInteraction.builder()
        .nag(nag)
        .member(member)
        .build();
  }

  public static NagInteraction ofMyNag(Nag nag, Member member) {
    return NagInteraction.builder()
        .nag(nag)
        .member(member)
        .build();
  }
}
