package com.a606.jansori.domain.notification.util;

import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
public class MessageUtil {

  private static final String TITLE = "육남매의 잔소리 폭격";
  private static final String MY_NAG_DELIVERED_BODY = "회원님의 잔소리가 다른 사용자에게 배달되었네요!";
  private static final String MY_TODO_GOT_NEW_NAG_BODY = "띵동! 회원님의 할 일에 잔소리가 배달되었어요!";
  private static final String NAG_LIKED_BODY = "사람들이 회원님의 잔소리를 좋아하고있어요!";
  private static final String TODO_WITH_YOUR_NAG_ACCOMPLISHED_BODY = "회원님의 잔소리 덕에 누군가 할 일을 마쳤어요!";
  private static final String SUBJECT_PREFIX_1 = "당신의 잔소리가 배달된 ";
  private static final String SUBJECT_PREFIX_2 = "당신의 잔소리가 ";
  private static final String SUBJECT_SUFFIX_1 = "님의 잔소리가 \"";
  private static final String SUBJECT_SUFFIX_2 = "님의 할 일 \"";
  private static final String SUBJECT_SUFFIX_3 = "님의 \"";
  private static final String SUBJECT_SUFFIX_4 = "님이 ";
  private static final String OBJECT_SUFFIX_GOT_NAG = "\"에 배달되었어요!";
  private static final String OBJECT_SUFFIX_ACCOMPLISHED = "\" Done!";
  private static final String OBJECT_SUFFIX_NAG_LIKED = "님의 잔소리를 좋아해요!";

  private Map<NotificationTypeName, String> notificationBodyHolder =
      Map.ofEntries(
          Map.entry(NotificationTypeName.MY_NAG_DELIVERED, MY_NAG_DELIVERED_BODY),
          Map.entry(NotificationTypeName.MY_TODO_GOT_NEW_NAG, MY_TODO_GOT_NEW_NAG_BODY),
          Map.entry(NotificationTypeName.NAG_LIKED, NAG_LIKED_BODY),
          Map.entry(NotificationTypeName.TODO_WITH_YOUR_NAG_ACCOMPLISHED, TODO_WITH_YOUR_NAG_ACCOMPLISHED_BODY)
      );

  public String getMessageBody(NotificationTypeName type) {
    return notificationBodyHolder.get(type);
  }

  public String getMessageTitle() {
    return TITLE;
  }
  public String getNotificationContent(NotificationTypeName type, String subject, String object) {

    StringBuilder stringBuilder = new StringBuilder();

    if (type.equals(NotificationTypeName.MY_TODO_GOT_NEW_NAG)){
      stringBuilder
          .append(subject)
          .append(SUBJECT_SUFFIX_1)
          .append(object)
          .append(OBJECT_SUFFIX_GOT_NAG);
    } else if (type.equals(NotificationTypeName.TODO_WITH_YOUR_NAG_ACCOMPLISHED)) {
      stringBuilder
          .append(SUBJECT_PREFIX_1)
          .append(subject)
          .append(SUBJECT_SUFFIX_2)
          .append(object)
          .append(OBJECT_SUFFIX_ACCOMPLISHED);

    } else if (type.equals(NotificationTypeName.MY_NAG_DELIVERED)) {
      stringBuilder
          .append(SUBJECT_PREFIX_2)
          .append(subject)
          .append(SUBJECT_SUFFIX_3)
          .append(object)
          .append(OBJECT_SUFFIX_GOT_NAG);
    } else {
      stringBuilder
          .append(subject)
          .append(SUBJECT_SUFFIX_4)
          .append(object)
          .append(OBJECT_SUFFIX_NAG_LIKED);
    }

    return stringBuilder.toString();
  }
}