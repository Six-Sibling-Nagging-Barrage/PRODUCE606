package com.a606.jansori.infra.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class PostFileUploadReqDto {

    private MultipartFile multipartFile;


}
