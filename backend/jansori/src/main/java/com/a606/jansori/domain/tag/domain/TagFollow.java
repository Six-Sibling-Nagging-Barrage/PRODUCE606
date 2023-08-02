package com.a606.jansori.domain.tag.domain;

import com.a606.jansori.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "tag_follow")
public class TagFollow {

    @Id
    @Column(name = "tag_follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static TagFollow fromTagAndMember(Tag tag, Member member) {
        return TagFollow.builder()
            .tag(tag)
            .member(member)
            .build();
    }
}
