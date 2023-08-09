package com.a606.jansori.global.exception.controller;

import com.a606.jansori.global.exception.domain.ForbiddenException;
import com.a606.jansori.global.exception.domain.UnauthorizedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

  @GetMapping("/access-denied")
  public void accessDeniedException() {
    throw new ForbiddenException();
  }
  @GetMapping("/unauthorized")
  public void unauthorizedException() {
    throw new UnauthorizedException();
  }
}
