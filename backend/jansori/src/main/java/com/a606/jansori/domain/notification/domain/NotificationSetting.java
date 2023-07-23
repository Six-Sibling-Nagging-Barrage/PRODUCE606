package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

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
    
}
