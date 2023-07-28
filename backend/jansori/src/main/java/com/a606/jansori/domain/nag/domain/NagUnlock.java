package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "nag_unlock")
public class NagUnlock {

    @Id
    @Column(name = "nag_unlock_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "nag_id")
    private Nag nag;
}
