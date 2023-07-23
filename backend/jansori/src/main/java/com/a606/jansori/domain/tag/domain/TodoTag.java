package com.a606.jansori.domain.tag.domain;

import com.a606.jansori.domain.todo.domain.Todo;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "todo_tag")
public class TodoTag {

    @Id
    @Column(name = "todo_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
