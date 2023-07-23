package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity(name = "notification_box")
public class NotificationBox {

    @Id
    @Column(name = "notification_box_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime readAt;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
