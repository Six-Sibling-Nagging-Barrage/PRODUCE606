package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostNagReqDto {

    @NotBlank
    private String content;

    @NotNull
    private Long tagId;
}
