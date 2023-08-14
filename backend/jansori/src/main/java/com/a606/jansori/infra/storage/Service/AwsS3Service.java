package com.a606.jansori.infra.storage.Service;

import com.a606.jansori.infra.storage.dto.DeleteFileReqDto;
import com.a606.jansori.infra.storage.dto.DeleteFileResDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadReqDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadResDto;
import com.a606.jansori.infra.storage.exception.FileUploadException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

  private final AmazonS3 amazonS3;
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public PostFileUploadResDto uploadImageFileToS3(PostFileUploadReqDto postFileUploadReqDto) {

    MultipartFile multipartFile = postFileUploadReqDto.getMultipartFile();

    String fileName = createFileName(multipartFile.getOriginalFilename());

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(multipartFile.getSize());
    objectMetadata.setContentType("image/jpeg");

    try (InputStream inputStream = multipartFile.getInputStream()) {
      uploadToS3(inputStream, objectMetadata, fileName);
    } catch (IOException e) {
      throw new FileUploadException();
    }

    String imageUrl = getFileUrl(fileName);

    return PostFileUploadResDto.builder()
        .imageUrl(imageUrl)
        .build();

  }

  private String createFileName(String originalFileName) {
    return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
  }

  private String getFileExtension(String fileName) {
    try {
      return fileName.substring(fileName.lastIndexOf("."));
    } catch (StringIndexOutOfBoundsException e) {
      throw new FileUploadException();
    }
  }

  public void uploadToS3(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
    amazonS3.putObject(
        new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(
            CannedAccessControlList.PublicRead));
  }

  public String getFileUrl(String fileName) {
    return String.valueOf(amazonS3.getUrl(bucket, fileName));
  }

  public DeleteFileResDto removeUploadedFile(DeleteFileReqDto deleteFileReqDto) {

    if (!amazonS3.doesObjectExist(bucket, deleteFileReqDto.getImageName())) {

      throw new AmazonS3Exception("대상이 " + deleteFileReqDto.getImageName() + " 존재하지 않습니다.");

    }

    amazonS3.deleteObject(bucket, deleteFileReqDto.getImageName());

    return DeleteFileResDto.builder()
        .deleted(true)
        .build();

  }

}