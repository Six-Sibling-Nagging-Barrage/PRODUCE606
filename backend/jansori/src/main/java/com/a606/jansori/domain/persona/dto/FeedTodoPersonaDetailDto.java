package com.a606.jansori.domain.persona.dto;


import com.a606.jansori.domain.persona.domain.TodoPersona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class FeedTodoPersonaDetailDto extends FeedTodoPersonaDto {

  private Long todoPersonaId;

  private String content;

  public static FeedTodoPersonaDetailDto from(TodoPersona todoPersona) {

    return FeedTodoPersonaDetailDto.builder()
        .todoPersonaId(todoPersona.getId())
        .personaId(todoPersona.getPersona().getId())
        .content(todoPersona.getLikeCount() > 0 ? todoPersona.getLine().getContent() : null)
        .likeCount(todoPersona.getLikeCount())
        .build();
  }
}
