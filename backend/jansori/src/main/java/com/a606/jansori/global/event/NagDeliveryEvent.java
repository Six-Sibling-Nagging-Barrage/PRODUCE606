package com.a606.jansori.global.event;

import com.a606.jansori.domain.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NagDeliveryEvent {

  private Todo todo;
}
