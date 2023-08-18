package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notification_box")
public class NotificationBox {

  @Id
  @Column(name = "notification_box_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private LocalDateTime readAt;

  @Column
  private LocalDateTime modifiedAt;

  @OneToOne
  @JoinColumn(name = "member_id")
  private Member member;

  public void updateReadAt(LocalDateTime now){
    this.readAt = now;
  }
  public void updateModifiedAt(LocalDateTime now) { this.modifiedAt = now; }

}
