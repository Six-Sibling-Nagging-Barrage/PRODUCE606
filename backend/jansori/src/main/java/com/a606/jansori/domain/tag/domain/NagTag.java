package com.a606.jansori.domain.tag.domain;

import com.a606.jansori.domain.nag.domain.Nag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "nag_tag")
public class NagTag {

    @Id
    @Column(name = "nag_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nag_id")
    private Nag nag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static NagTag of(Nag nag, Tag tag) {
        return NagTag.builder()
                .nag(nag)
                .tag(tag)
                .build();
    }
}
