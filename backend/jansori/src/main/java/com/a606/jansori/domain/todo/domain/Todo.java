package com.a606.jansori.domain.todo.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.global.common.BaseTimeEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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
  private LocalDate todoAt;

  @Column
  private Boolean display;

  @Column
  private Boolean finished;

  @Column
  private String content;

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST)
  @Builder.Default
  private List<TodoTag> todoTags = new ArrayList<>();

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST)
  @Builder.Default
  private List<TodoPersona> todoPersonas = new ArrayList<>();

  public void setNag(Nag nag) {
    this.nag = nag;
  }

  public Boolean toggleFinished() {

    this.finished = !this.finished;

    return this.finished;
  }
}
