package com.a606.jansori.domain.persona.dto;

import com.a606.jansori.domain.persona.domain.TodoPersona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedLineDto {

  private Long personaId;

  private String content;

  private Long likeCount;

  public static FeedLineDto from(TodoPersona todoPersona) {

    return FeedLineDto.builder()
        .personaId(todoPersona.getPersona().getId())
        .content(todoPersona.getLikeCount() > 0 ? todoPersona.getLine().getContent() : null)
        .likeCount(todoPersona.getLikeCount())
        .build();
  }
}
