package com.a606.jansori.domain.persona.domain;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.todo.domain.Todo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TodoPersona {

  @Id
  @Column(name = "todo_metadata_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "todo_id")
  private Todo todo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "persona_id")
  private Persona persona;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nag_id")
  private Nag nag;

  @Column
  @Builder.Default
  private Long likeCount = 1L;

  public void setTodo(Todo todo) {

    if (todo != null) {
      this.todo.getTodoPersonas().remove(this);
    }

    this.todo = todo;
    this.todo.getTodoPersonas().add(this);
  }
}
