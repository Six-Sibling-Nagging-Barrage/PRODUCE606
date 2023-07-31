package com.a606.jansori.domain.member.domain;

import com.a606.jansori.global.common.BaseTimeEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Entity(name = "member")
public class Member extends BaseTimeEntity {

  @Id
  @Column(name = "member_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private MemberRole memberRole;

  @Column
  private String oauthIdentifier;

  @Enumerated(EnumType.STRING)
  private OauthType oauthType;

  @Column
  private String nickname;

  @Column
  private String bio;

  @Column
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  private MemberStatus memberState;

  @Column
  @Builder.Default
  private Long ticket = 0L;

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }

    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }

    Member member = (Member) obj;
    return this.getId() == member.getId();
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId());
  }

  public void useTicketByNagUnlock() {
    if (ticket > 0) {
      ticket -= 1;
    }
  }
}
