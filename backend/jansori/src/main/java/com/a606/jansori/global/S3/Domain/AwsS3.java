package com.a606.jansori.global.S3.Domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AwsS3 {

  private String key;
  private String path;

}