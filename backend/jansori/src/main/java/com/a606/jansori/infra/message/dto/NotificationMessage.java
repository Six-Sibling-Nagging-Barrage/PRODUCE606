package com.a606.jansori.infra.message.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {

  private String recipientToken;
  private String title;
  private String body;
  private String image;
  private Map<String, String > data;

}
