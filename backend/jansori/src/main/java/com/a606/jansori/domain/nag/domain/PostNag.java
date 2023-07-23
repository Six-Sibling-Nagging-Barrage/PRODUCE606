package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.todo.domain.Todo;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "post_nag")
public class PostNag {

    @Id
    @Column(name = "post_nag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "nag_id")
    private Nag nag;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;
}
