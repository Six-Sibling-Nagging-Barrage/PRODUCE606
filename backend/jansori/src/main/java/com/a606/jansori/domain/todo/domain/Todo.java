package com.a606.jansori.domain.todo.domain;

import com.a606.jansori.global.common.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "todo")
public class Todo extends BaseTimeEntity {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
