package com.a606.jansori.dummy;


import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.service.NagRandomGenerator;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.repository.TodoTagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.dummy.DummyProperty.TodoDummy;
import com.a606.jansori.global.util.RandomUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TodoWithTodoTagDummy {

  private final DummyProperty dummyProperty;
  private final TodoRepository todoRepository;
  private final List<Todo> allTodos = new ArrayList<>();
  private final NagRandomGenerator nagRandomGenerator;

  //멤버는 todo 를 생성하고,
  // todo 는 해시태그를 최소 0개에서 최대3개를 가질 수 있고
  //todo 는 잔소리를 한개 가질 수 있음
  public List<Todo> createTodosWithTodoTags(List<Member> members, List<Tag> tags) {

    List<TodoDummy> todoDummies = dummyProperty.getTodoDummies();
    int memberSize = members.size();

    for (int i = 7; i >= 0; i--) {
      for (TodoDummy todoDummy : todoDummies) {
        int randMemberIdx = (int) (Math.random() * memberSize);

        Todo todo = Todo.builder()
            .member(members.get(randMemberIdx))
            .content(todoDummy.getContent())
            .finished((int) (Math.random() * 2) > 1)
            .display(true)
            .todoAt(LocalDate.now().minusDays(i))
            .build();

        List<TodoTag> todoTags = todoDummy.getTags().stream()
            .map(tagString -> {
              Tag tag = tags.stream()
                  .filter(tag1 -> tag1.getName().equals(tagString))
                  .findFirst()
                  .orElse(null);

              TodoTag todoTag = TodoTag.builder()
                  .tag(tag)
                  .todo(todo)
                  .build();

              todoTag.setTodo(todo);

              return todoTag;

            }).collect(Collectors.toList());

        todo.setNag(nagRandomGenerator.getRandomNagWithTags(members.get(randMemberIdx), todoTags)
            .orElse(null));

        allTodos.add(todo);
      }
    }

    return todoRepository.saveAll(allTodos);
  }
}
