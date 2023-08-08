package com.a606.jansori.domain.member.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatchMemberNotificationSettingReqDto {

//  List<Boolean> notificationSettings;

  Map<Long, Boolean> notificationSettings; // 1L : true, 2L : false
  // nagOnMyTodo : true, myNagOnOtherTodo : false
  Long memberId;
}