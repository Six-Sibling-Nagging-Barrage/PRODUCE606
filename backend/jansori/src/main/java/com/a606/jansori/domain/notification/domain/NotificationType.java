package com.a606.jansori.domain.notification.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "notification_type")
public class NotificationType {

    @Id
    @Column(name = "notification_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
