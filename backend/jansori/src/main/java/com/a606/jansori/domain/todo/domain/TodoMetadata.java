package com.a606.jansori.domain.todo.domain;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.persona.domain.Persona;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TodoMetadata {

    @Id
    @Column(name = "todo_metadata_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nag_id")
    private Nag nag;

    @Column
    private Long count;
}
