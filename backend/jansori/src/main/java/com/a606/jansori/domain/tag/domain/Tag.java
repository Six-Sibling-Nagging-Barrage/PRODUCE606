package com.a606.jansori.domain.tag.domain;

import com.a606.jansori.domain.todo.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column
    private Long count;

    public static Tag from(TagDto tagDto) {
        return Tag.builder()
                .name(tagDto.getTagName())
                .count(1L)
                .build();
    }


}