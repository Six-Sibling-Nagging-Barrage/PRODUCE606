package com.a606.jansori.dummy;


import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.repository.TodoTagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.util.RandomUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TodoWithTodoTagDummy {

  private final TodoRepository todoRepository;
  private final TodoTagRepository todoTagRepository;

  private final List<Todo> todos = new ArrayList<>();
  private final List<TodoTag> todoTags = new ArrayList<>();

  private final RandomUtil randomUtil;

  //멤버는 todo 를 생성하고,
  // todo 는 해시태그를 최소 0개에서 최대3개를 가질 수 있고
  //todo 는 잔소리를 한개 가질 수 있음
  public List<Todo> createTodosWithTodoTags(List<Member> members, List<Tag> tags, List<Nag> nags) {
    int start = 0;

    for (int i = 30; i >= 0; i--) {
      for (int j = 0; j < members.size(); j++) {
        for (int k = 0; k < 2; k++) { // 멤버당 하루에 todo 2개씩
          Todo todo = Todo.builder()
              .member(members.get(j))
              .content("오늘의 할일" + start++)
              .finished(false)
              .display(k == 1 ? false : true)
              .todoAt(LocalDate.now().minusDays(i))
              .nag(nags.get(randomUtil.generate((long) nags.size())))
              .build();

          todos.add(todo);

          TodoTag todoTag = TodoTag.builder()
              .tag(tags.get(randomUtil.generate((long) tags.size())))
              .todo(todo).build();

          todoTags.add(todoTag);
        }
      }
    }
    todoTagRepository.saveAll(todoTags);
    return todoRepository.saveAll(todos);
  }
}
