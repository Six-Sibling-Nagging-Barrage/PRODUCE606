package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
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

    @Enumerated(EnumType.STRING)
    private NagType nagType;

    @Column
    private Long writerId;

    @Column
    private String preview;

    public Nag(Long memberId, PostNagReqDto postNagReqDto) {
        Nag.builder()
                .writerId(memberId)
                .nagType(NagType.MEMBER)
                .content(postNagReqDto.getContent())
                .build();
    }
}
