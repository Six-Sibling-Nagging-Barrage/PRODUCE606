package com.a606.jansori.domain.persona.domain;

import com.a606.jansori.global.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity(name = "line")
public class Line extends BaseTimeEntity {

  @Id
  @Column(name = "line_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @ManyToOne
  @JoinColumn(name = "persona_id")
  private Persona persona;
}
