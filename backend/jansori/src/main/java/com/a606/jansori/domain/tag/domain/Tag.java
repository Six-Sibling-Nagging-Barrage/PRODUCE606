package com.a606.jansori.domain.tag.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "tag")
public class Tag {

  @Id
  @Column(name = "tag_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column(name = "follow_count")
  @Builder.Default
  private Integer followCount = 0;

  public void decreaseFollowCount() {
    if (followCount > 0) {
      followCount -= 1;
    }
  }

  public void increaseFollowCount() {
    followCount += 1;
  }
}