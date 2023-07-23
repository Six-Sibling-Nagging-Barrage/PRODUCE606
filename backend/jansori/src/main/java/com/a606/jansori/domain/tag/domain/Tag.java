package com.a606.jansori.domain.tag.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "tag")
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long count;
}
