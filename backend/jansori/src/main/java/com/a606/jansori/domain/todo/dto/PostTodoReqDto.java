package com.a606.jansori.domain.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostTodoReqDto {

    private String content;

    private Boolean display;

    private List<TagDto> tags;
}
