package com.a606.jansori.domain.persona.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "persona_reaction")
public class PersonaReaction {

    @Id
    @Column(name = "persona_reaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private TodoPersona todoPersona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;
}
