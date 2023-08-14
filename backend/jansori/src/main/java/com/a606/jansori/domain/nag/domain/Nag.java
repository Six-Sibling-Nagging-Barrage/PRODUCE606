package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.global.common.BaseTimeEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "nag")
public class Nag extends BaseTimeEntity {

  @Id
  @Column(name = "nag_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @Column
  private String preview;

  @Column(name = "like_count")
  @Builder.Default
  private Integer likeCount = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id")
  private Tag tag;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "nag", cascade = CascadeType.ALL)
  private Set<Todo> todos = new HashSet<>();

  @OneToMany(mappedBy = "nag", cascade = CascadeType.ALL)
  private Set<NagUnlock> nagUnlocks = new HashSet<>();

  @OneToMany(mappedBy = "nag", cascade = CascadeType.ALL)
  private Set<NagLike> nagLikes = new HashSet<>();

  public static Nag ofMemberWithNagContentAndPreview(Member member, Tag tag,
      String content, String preview) {
    return Nag.builder()
        .member(member)
        .tag(tag)
        .content(content)
        .preview(preview)
        .build();
  }

  public void decreaseLikeCount() {
    if (likeCount > 0) {
      likeCount -= 1;
    }
  }

  public void increaseLikeCount() {
    likeCount += 1;
  }
}
