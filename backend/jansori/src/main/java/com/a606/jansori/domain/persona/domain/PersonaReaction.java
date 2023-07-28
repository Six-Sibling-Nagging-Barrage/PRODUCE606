package com.a606.jansori.domain.persona.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.todo.domain.Todo;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "persona_reaction")
public class PersonaReaction {

    @Id
    @Column(name = "persona_reaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;
}
