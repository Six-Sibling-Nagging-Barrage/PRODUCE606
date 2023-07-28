package com.a606.jansori.global.util;

import org.springframework.stereotype.Component;

@Component
public class RandomUtil {

  public int generate(Long maxIndex){

    return (int) (Math.random() * maxIndex) + 1;
  }
}
