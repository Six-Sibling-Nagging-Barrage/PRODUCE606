package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.global.common.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "nag")
public class Nag extends BaseTimeEntity {

    @Id
    @Column(name = "nag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    private NagType nagType;

    @Column
    private Long writerId;

    @Column
    private String preview;
}
