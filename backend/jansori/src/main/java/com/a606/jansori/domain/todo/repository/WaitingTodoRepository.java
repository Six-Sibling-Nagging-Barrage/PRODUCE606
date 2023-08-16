package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.todo.domain.WaitingTodo;
import org.springframework.data.repository.CrudRepository;

public interface WaitingTodoRepository extends CrudRepository<WaitingTodo, String> {

}
