package com.a606.jansori.domain.todo.domain;

import com.a606.jansori.domain.nag.domain.NagInteraction;
import com.a606.jansori.domain.todo.dto.TodoFeedDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TodoFeeds {

  List<TodoFeedDto> todoFeeds;

  public TodoFeeds(List<Todo> todos, List<NagInteraction> nagInteractions) {
    makeTodoFeeds(todos);
    matchNagUnlockWithLikeStatus(nagInteractions);
  }

  private void makeTodoFeeds(List<Todo> todos) {
    todoFeeds = todos.stream()
        .map(todo -> TodoFeedDto.from(todo, false, false))
        .collect(Collectors.toList());
  }

  private void matchNagUnlockWithLikeStatus(List<NagInteraction> nagInteractions) {
    nagInteractions.forEach(this::changeNagUnlockWithLikeStatus);
  }

  private void changeNagUnlockWithLikeStatus(NagInteraction nagInteraction) {
    for (TodoFeedDto todoFeed : todoFeeds) {
      if(todoFeed.getFeedNagDto() == null) {
        continue;
      }
      if (todoFeed.getFeedNagDto().getNagId().equals(nagInteraction.getNag().getId())) {
        todoFeed.changeNagLikedAndUnlockStatus(nagInteraction);
      }
    }
  }
}
