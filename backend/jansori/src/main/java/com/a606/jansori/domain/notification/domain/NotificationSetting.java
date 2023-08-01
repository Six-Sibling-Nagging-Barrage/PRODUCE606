package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity(name = "notification_setting")
public class NotificationSetting {

  @Id
  @Column(name = "notification_setting_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "notification_type_id")
  private NotificationType notificationType;

  @Column
  private Boolean activated;

  public void update(Boolean activated){
    this.activated = activated;
  }

}
