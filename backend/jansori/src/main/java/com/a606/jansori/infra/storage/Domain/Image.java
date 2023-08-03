package com.a606.jansori.infra.storage.Domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Image {

  private String imageName;
  private String imageDir;

}