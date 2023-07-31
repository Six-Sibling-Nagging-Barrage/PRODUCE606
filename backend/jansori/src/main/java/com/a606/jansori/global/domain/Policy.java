package com.a606.jansori.global.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "policy")
public class Policy {

    @Id
    @Column(name = "policy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long version;

    @Column
    private String content;
}
