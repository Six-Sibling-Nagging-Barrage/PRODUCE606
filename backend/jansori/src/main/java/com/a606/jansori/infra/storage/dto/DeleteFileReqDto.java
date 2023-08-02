package com.a606.jansori.infra.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteFileReqDto {

    private String key;
    private String path;

}
