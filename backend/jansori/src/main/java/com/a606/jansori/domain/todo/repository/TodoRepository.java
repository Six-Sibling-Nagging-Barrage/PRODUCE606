package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
