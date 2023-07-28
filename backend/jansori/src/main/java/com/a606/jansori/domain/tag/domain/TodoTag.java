package com.a606.jansori.domain.tag.domain;

import com.a606.jansori.domain.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "todo_tag")
public class TodoTag {

    @Id
    @Column(name = "todo_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void setTodo(Todo todo) {

        this.todo = todo;

        todo.getTodoTags().add(this);
    }

    public TodoTag(Tag tag) {

        this.tag = tag;
    }

    public TodoTag(Tag tag, Todo todo) {

        this.tag = tag;
        this.setTodo(todo);
    }
}
