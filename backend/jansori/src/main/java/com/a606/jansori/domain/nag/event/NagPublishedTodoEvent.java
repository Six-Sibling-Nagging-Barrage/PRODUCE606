package com.a606.jansori.domain.nag.event;

import com.a606.jansori.domain.nag.domain.Nag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NagPublishedTodoEvent {

  private Nag nag;
}
