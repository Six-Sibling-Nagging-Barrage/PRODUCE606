package com.a606.jansori.infra.storage.Service;

import com.a606.jansori.infra.storage.dto.DeleteFileReqDto;
import com.a606.jansori.infra.storage.dto.DeleteFileResDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadReqDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadResDto;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;
    private final ResourceLoader resourceLoader;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PostFileUploadResDto uploadFile(PostFileUploadReqDto postFileUploadReqDto, String dirName)
            throws IOException {

        MultipartFile multipartFile = postFileUploadReqDto.getMultipartFile();

        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert fail"));

        String key = dirName + "/" + UUID.randomUUID() + file.getName();
        String path = putS3(file, key);

        file.delete();

        return PostFileUploadResDto.builder()
                .imageName(key)
                .imageDir(path)
                .build();

    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {

        String staticFolderPath = Paths.get(resourceLoader.
                getResource("classpath:static/tmp/").getURI()).toString();

        File tmpDir = new File(staticFolderPath);
        if (!tmpDir.exists()) {
            Files.createDirectories(tmpDir.toPath());
        }

        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        File file = new File(tmpDir, fileName);
        Files.copy(multipartFile.getInputStream(), file.toPath());

        return Optional.of(file);

    }

    private String putS3(File uploadFile, String fileName) {

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return getS3(bucket, fileName);

    }

    private String getS3(String bucket, String fileName) {

        return amazonS3.getUrl(bucket, fileName).toString();

    }

    public DeleteFileResDto removeUploadedFile(DeleteFileReqDto deleteFileReqDto) {

        if (!amazonS3.doesObjectExist(bucket, deleteFileReqDto.getKey())) {

            throw new AmazonS3Exception("대상이 " + deleteFileReqDto.getKey() + " 존재하지 않습니다.");

        }

        amazonS3.deleteObject(bucket, deleteFileReqDto.getKey());

        return DeleteFileResDto.builder()
                .deleted(true)
                .build();

    }

}