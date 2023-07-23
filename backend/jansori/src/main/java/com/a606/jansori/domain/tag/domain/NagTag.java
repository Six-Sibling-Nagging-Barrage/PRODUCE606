package com.a606.jansori.domain.tag.domain;

import com.a606.jansori.domain.nag.domain.Nag;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "nag_tag")
public class NagTag {

    @Id
    @Column(name = "nag_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nag_id")
    private Nag nag;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
