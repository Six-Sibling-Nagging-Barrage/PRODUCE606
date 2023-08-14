package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.validator.NagContent;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.validator.TagName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostNagReqDto {

    @NagContent
    private String content;

    @NotNull
    private Long tagId;

    @TagName
    @NotBlank
    private String tagName;
}
