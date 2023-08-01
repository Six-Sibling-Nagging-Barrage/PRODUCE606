package com.a606.jansori.domain.member.dto;

import java.util.List;
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

  public static PatchMemberNotificationSettingResDto from(List<Boolean> settings){
    return PatchMemberNotificationSettingResDto.builder()
        .nagOnMyTodo(settings.get(0))
        .myNagOnOtherTodo(settings.get(1))
        .reactionOnMyNag(settings.get(2))
        .completionOfNaggedTodo(settings.get(3))
        .build();
  }

}
