package com.a606.jansori.domain.member.domain;

import com.a606.jansori.global.common.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String oauthIdentifier;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Column
    private String nickname;

    @Column
    private String bio;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberState;

    @Column
    private Long ticket;

}
