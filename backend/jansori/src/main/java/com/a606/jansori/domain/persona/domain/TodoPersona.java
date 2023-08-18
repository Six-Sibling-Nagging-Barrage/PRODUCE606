package com.a606.jansori.domain.persona.domain;

import com.a606.jansori.domain.todo.domain.Todo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "todo_persona")
public class TodoPersona {

  @Id
  @Column(name = "todo_persona_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "todo_id")
  private Todo todo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "persona_id")
  private Persona persona;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "line_id")
  private Line line;

  @Column
  @Builder.Default
  private Integer likeCount = 0;

  public void setTodo(Todo todo) {

    if (this.todo != null) {
      this.todo.getTodoPersonas().remove(this);
    }

    this.todo = todo;
    this.todo.getTodoPersonas().add(this);
  }

  public Integer increaseLikeCount() {
    return ++this.likeCount;
  }

  public void setLine(Line line) {
    this.line = line;
  }
}
