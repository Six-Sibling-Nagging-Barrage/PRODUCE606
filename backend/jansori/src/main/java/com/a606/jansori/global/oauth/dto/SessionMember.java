package com.a606.jansori.global.oauth.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private Long memberId;

    public SessionMember(Member member){
        this.memberId = member.getId();
    }


}
