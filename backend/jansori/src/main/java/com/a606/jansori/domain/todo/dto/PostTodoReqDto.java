package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.todo.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostTodoReqDto {


    @Length(min = 2, max = 30, message = "Todo의 길이는 2에서 30 사이입니다.")
    private String content;

    private Boolean display;

    private List<TagDto> tags;

    public Todo getTodoWith(Member member) {
        return Todo.builder().member(member)
                .display(this.getDisplay())
                .finished(Boolean.FALSE)
                .content(this.getContent())
                .build();
    }
}
