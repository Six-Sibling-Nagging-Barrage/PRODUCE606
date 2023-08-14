package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.dto.TagDto;
import com.a606.jansori.domain.todo.domain.Todo;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class PostTodoReqDto {

  @Length(min = 2, max = 30, message = "Todo의 길이는 2에서 30 사이입니다.")
  private String content;

  private Boolean display;

  private List<TagDto> tags;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate todoAt;

  public Todo getTodoWith(Member member) {
    return Todo.builder().member(member)
        .todoAt(todoAt)
        .display(this.getDisplay())
        .finished(Boolean.FALSE)
        .content(this.getContent())
        .build();
  }

  public boolean isAllNewTags() {
    boolean isAllNew = true;

    for(TagDto tagDto : tags) {
      if(tagDto.getTagId() != -1) {
        isAllNew = false;
        break;
      }
    }

    return isAllNew;
  }
}
