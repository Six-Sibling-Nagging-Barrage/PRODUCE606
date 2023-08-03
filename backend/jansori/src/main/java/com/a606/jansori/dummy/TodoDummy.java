package com.a606.jansori.dummy;


import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TodoDummy {

  private final TodoRepository todoRepository;

  private final List<Todo> todos = new ArrayList<>();

  //멤버가 todo를 생성하고, todo는 해시태그를 최소 0개에서 최대3개를 가질 수 있고
  //todo는 잔소리를 한개 가질 수 있음
  public List<Todo> createTodos()
}
