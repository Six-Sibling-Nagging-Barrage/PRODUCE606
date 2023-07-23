package com.a606.jansori.domain.todo.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.global.common.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "todo")
public class Todo extends BaseTimeEntity {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private Boolean display;

    @Column
    private Boolean finished;

    @Column
    private String content;

    @OneToMany(mappedBy = "todo")
    private List<TodoTag> todoTags = new ArrayList<>();
}
