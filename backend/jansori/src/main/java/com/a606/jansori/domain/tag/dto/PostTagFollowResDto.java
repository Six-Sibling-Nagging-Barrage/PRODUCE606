package com.a606.jansori.domain.tag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class PostTagFollowResDto {

  private Boolean isFollowed;

  public void cancelFollowingTag() {
    isFollowed = false;
  }

  public void followingTag() {
    isFollowed = true;
  }
}