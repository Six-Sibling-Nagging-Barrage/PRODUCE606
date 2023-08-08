package com.a606.jansori.domain.member.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatchMemberNotificationSettingResDto {

  private Boolean nagOnMyTodo;
  private Boolean myNagOnOtherTodo;
  private Boolean reactionOnMyNag;
  private Boolean completionOfNaggedTodo;

  public static PatchMemberNotificationSettingResDto from(Map<Long, Boolean> settings) {
    return PatchMemberNotificationSettingResDto.builder()
        .nagOnMyTodo(settings.get(1L))
        .myNagOnOtherTodo(settings.get(2L))
        .reactionOnMyNag(settings.get(3L))
        .completionOfNaggedTodo(settings.get(4L))
        .build();
  }

}