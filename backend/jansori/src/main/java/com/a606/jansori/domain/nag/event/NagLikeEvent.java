package com.a606.jansori.domain.nag.event;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.notification.domain.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NagLikeEvent {

  private Member member;
  private Nag nag;
  private NotificationType notificationType;
}
