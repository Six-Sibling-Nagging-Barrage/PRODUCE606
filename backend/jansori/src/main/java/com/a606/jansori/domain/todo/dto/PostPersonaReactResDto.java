package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.dto.FeedTodoPersonaDetailDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PostPersonaReactResDto extends FeedTodoPersonaDetailDto {

  private Boolean isFirstReaction;

  public static PostPersonaReactResDto from(TodoPersona todoPersona) {

    return PostPersonaReactResDto.builder()
        .isFirstReaction(todoPersona.getLikeCount() == 1)
        .todoPersonaId(todoPersona.getId())
        .personaId(todoPersona.getPersona().getId())
        .content(todoPersona.getLikeCount() == 1 ? todoPersona.getLine().getContent() : null)
        .likeCount(todoPersona.getLikeCount())
        .build();
  }
}
