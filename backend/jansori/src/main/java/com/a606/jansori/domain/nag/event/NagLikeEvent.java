package com.a606.jansori.domain.nag.event;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import lombok.Getter;

@Getter
public class NagLikeEvent {

  private Member member;
  private Nag nag;

  public NagLikeEvent(Member member, Nag nag){
    this.member = member;
    this.nag = nag;
  }
}
