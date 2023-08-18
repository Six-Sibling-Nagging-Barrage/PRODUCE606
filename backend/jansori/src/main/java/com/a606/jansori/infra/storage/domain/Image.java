package com.a606.jansori.infra.storage.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Image {

  private String imageName;
  private String imageDir;

}