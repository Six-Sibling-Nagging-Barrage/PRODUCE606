package com.a606.jansori.domain.todo.event;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostTodoEvent {

    private Todo todo;
    private NotificationType notificationType;
}
