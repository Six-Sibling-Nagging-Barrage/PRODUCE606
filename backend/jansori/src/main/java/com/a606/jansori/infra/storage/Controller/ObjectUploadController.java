package com.a606.jansori.infra.storage.Controller;

import com.a606.jansori.infra.storage.exception.FileUploadException;
import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.storage.Service.AwsS3Service;
import com.a606.jansori.infra.storage.dto.DeleteFileReqDto;
import com.a606.jansori.infra.storage.dto.DeleteFileResDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadReqDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadResDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class ObjectUploadController {

  private final AwsS3Service awsS3Service;


  @PostMapping(value = "/upload")
  public EnvelopeResponse<PostFileUploadResDto> upload(
      @RequestPart("file") MultipartFile multipartFile) {

    return EnvelopeResponse.<PostFileUploadResDto>builder()
        .data(awsS3Service.uploadFile(PostFileUploadReqDto.builder()
            .multipartFile(multipartFile)
            .build()))
        .build();

  }

  @DeleteMapping("/delete")
  public EnvelopeResponse<DeleteFileResDto> remove(@RequestBody DeleteFileReqDto deleteFileReqDto) {

    return EnvelopeResponse.<DeleteFileResDto>builder()
        .data(awsS3Service.removeUploadedFile(deleteFileReqDto))
        .build();

  }


}