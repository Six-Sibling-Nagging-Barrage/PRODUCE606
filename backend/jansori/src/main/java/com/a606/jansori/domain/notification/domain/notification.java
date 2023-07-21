package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class notification {

    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id")
    private NotificationType notificationType;

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
