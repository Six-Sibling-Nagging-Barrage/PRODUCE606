package com.a606.jansori.domain.member.domain;

import com.a606.jansori.global.domain.Policy;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity(name = "member_policy")
public class MemberPolicy {

  @Id
  @Column(name = "member_policy_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "policy_id")
  private Policy policy;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column
  private Boolean approval;
}
