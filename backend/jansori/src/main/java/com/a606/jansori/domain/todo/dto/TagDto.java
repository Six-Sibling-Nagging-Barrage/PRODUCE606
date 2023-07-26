package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.tag.domain.TodoTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TagDto {

    private Long tagId;

    private String tagName;

    public static TagDto from(TodoTag todoTag) {

        return TagDto.builder()
                .tagId(todoTag.getTag().getId())
                .tagName(todoTag.getTag().getName())
                .build();
    }
}
