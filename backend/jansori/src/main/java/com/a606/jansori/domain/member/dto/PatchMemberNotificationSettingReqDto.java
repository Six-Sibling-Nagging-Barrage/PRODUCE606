package com.a606.jansori.domain.member.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class PatchMemberNotificationSettingReqDto {

  List<Boolean> notificationSettings;

}

