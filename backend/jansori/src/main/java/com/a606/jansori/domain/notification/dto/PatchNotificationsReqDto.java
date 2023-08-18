package com.a606.jansori.domain.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchNotificationsReqDto {

  private Long cursor;

  private Integer size;

  public PatchNotificationsReqDto() {
    this.size = 10;
  }
}
