package com.a606.jansori.infra.storage.Controller;

import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.storage.Service.AwsS3Service;
import com.a606.jansori.infra.storage.dto.DeleteFileReqDto;
import com.a606.jansori.infra.storage.dto.DeleteFileResDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadReqDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class ObjectUploadController {

    private final AwsS3Service awsS3Service;
    private final String UPLOAD_DIR = "upload";

    @PostMapping(value = "/upload")
    public EnvelopeResponse<PostFileUploadResDto> upload(@RequestPart("file") MultipartFile multipartFile)
            throws IOException {

        return EnvelopeResponse.<PostFileUploadResDto>builder()
                .data(awsS3Service.uploadFile(PostFileUploadReqDto.builder()
                        .multipartFile(multipartFile)
                        .build(), UPLOAD_DIR))
                .build();

    }

    @DeleteMapping("/delete")
    public EnvelopeResponse<DeleteFileResDto> remove(@RequestBody DeleteFileReqDto deleteFileReqDto) {

        return EnvelopeResponse.<DeleteFileResDto>builder()
                .data(awsS3Service.removeUploadedFile(deleteFileReqDto))
                .build();

    }


}