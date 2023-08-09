package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
import com.a606.jansori.global.common.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="notification")
public class Notification extends BaseTimeEntity {

  @Id
  @Column(name = "notification_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member receiver;

  @Enumerated(EnumType.STRING)
  private TalkerType talkerType;

  @Column
  private Long talkerId;
}
