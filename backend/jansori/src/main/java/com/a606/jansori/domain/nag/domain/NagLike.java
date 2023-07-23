package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "nag_like")
public class NagLike {

    @Id
    @Column(name = "nag_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nag_id")
    private Nag nag;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
