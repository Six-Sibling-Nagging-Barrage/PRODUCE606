package com.a606.jansori.domain.persona.repository;

import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.todo.domain.Todo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoPersonaRepository extends JpaRepository<TodoPersona, Long> {

  @Query(value = "SELECT tp from todo_persona tp "
      + "LEFT JOIN FETCH tp.todo td "
      + "LEFT JOIN FETCH tp.persona p "
      + "LEFT JOIN FETCH tp.line l "
      + "WHERE tp.todo = :todo")
  List<TodoPersona> findAllByTodo(Todo todo);

  Optional<TodoPersona> findByTodo_IdAndPersona_Id(Long todoId, Long personaId);
}
