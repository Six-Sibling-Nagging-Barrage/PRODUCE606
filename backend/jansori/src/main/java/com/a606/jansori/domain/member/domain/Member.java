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
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "member")
public class Member extends BaseTimeEntity {

  @Id
  @Column(name = "member_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String email;

  @Column
  private String password;

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
  private Long ticket = 50L;

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

  public void update(String nickname, String bio, String imageUrl, MemberRole role) {
    this.nickname = nickname;
    this.bio = bio;
    this.imageUrl = imageUrl;
    this.memberRole = role;
  }

  public void consumeTicketToUnlockNag() {
    if (ticket > 0) {
      ticket -= 1;
    }
  }

  public Member(String email, String password, MemberRole memberRole){
    this.email = email;
    this.password = password;
    this.memberRole = memberRole;
  }
}
