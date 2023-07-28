package com.a606.jansori.domain.todo.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "todo")
public class Todo extends BaseTimeEntity {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nag_id")
    private Nag nag;

    @Column
    private Boolean display;

    @Column
    private Boolean finished;

    @Column
    private String content;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<TodoTag> todoTags = new ArrayList<>();

    @OneToMany(mappedBy = "todo_id", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<TodoPersona> todoPersonas = new ArrayList<>();

    public void setNag(Nag nag) {
        this.nag = nag;
    }
}
