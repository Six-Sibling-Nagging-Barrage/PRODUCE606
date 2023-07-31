package com.a606.jansori.domain.persona.dto;

import com.a606.jansori.domain.persona.domain.TodoPersona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FeedTodoPersonaDto {

  private Long todoPersonaId;

  private Long personaId;

  private Integer likeCount;

  public static FeedTodoPersonaDto from(TodoPersona todoPersona) {

    return FeedTodoPersonaDto.builder()
        .todoPersonaId(todoPersona.getId())
        .personaId(todoPersona.getPersona().getId())
        .likeCount(todoPersona.getLikeCount())
        .build();
  }
}
