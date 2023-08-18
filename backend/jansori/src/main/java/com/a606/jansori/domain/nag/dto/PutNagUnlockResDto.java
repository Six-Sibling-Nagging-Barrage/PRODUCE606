package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class PutNagUnlockResDto extends NagDto {

  private Long ticketCount;

  public static PutNagUnlockResDto ofNagUnlockWithTicketCount(Nag nag, Long ticketCount) {
    return PutNagUnlockResDto.builder()
        .nagId(nag.getId())
        .content(nag.getContent())
        .createAt(nag.getCreatedAt())
        .ticketCount(ticketCount)
        .build();
  }
}
