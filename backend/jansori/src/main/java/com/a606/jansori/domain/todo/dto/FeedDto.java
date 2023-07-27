package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.a606.jansori.domain.nag.dto.FeedNagDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedDto {

    @JsonProperty("todoMember")
    private FeedMemberDto feedMemberDto;

    @JsonProperty("todo")
    private TodoDto todoDto;

    @JsonProperty("nag")
    private FeedNagDto nag;

    public static FeedDto fromTodo(Todo todo) {

        return FeedDto.builder()
                .feedMemberDto(FeedMemberDto.from(todo.getMember()))
                .todoDto(TodoDto.from(todo))
                .nag()
                .build();
    }

}
