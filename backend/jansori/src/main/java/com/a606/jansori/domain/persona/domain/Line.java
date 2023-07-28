package com.a606.jansori.domain.persona.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity(name = "linw")
public class Line {

  @Id
  @Column(name = "nag_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @ManyToOne
  @JoinColumn(name = "persona_id")
  Persona persona;
}
