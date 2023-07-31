package com.a606.jansori.domain.todo.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Builder
public class PostPersonaReactReqDto {

  @NumberFormat
  private Long personaId;

}
