package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.event.NagLikeEvent;
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
import org.springframework.context.ApplicationEventPublisher;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "nag_interaction")
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
}
