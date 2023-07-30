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

  private Long personaId;

  private String content;

  private Integer likeCount;

  public static FeedTodoPersonaDto from(TodoPersona todoPersona) {

    return FeedTodoPersonaDto.builder()
        .personaId(todoPersona.getPersona().getId())
        .content(todoPersona.getLikeCount() > 0 ? todoPersona.getLine().getContent() : null)
        .likeCount(todoPersona.getLikeCount())
        .build();
  }
}
