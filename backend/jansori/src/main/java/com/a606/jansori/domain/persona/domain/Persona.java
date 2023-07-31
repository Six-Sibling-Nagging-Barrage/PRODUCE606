package com.a606.jansori.domain.persona.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "persona")
public class Persona {

    @Id
    @Column(name = "persona_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String imageUrl;

    @Column
    private String bio;

}
