package com.a606.jansori.global.exception.advice;

import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.global.exception.BadRequestException;
import com.a606.jansori.global.exception.ForbiddenException;
import com.a606.jansori.global.exception.InternalException;
import com.a606.jansori.global.exception.NotFoundException;
import com.a606.jansori.global.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public EnvelopeResponse BadRequestExceptionHandler(BindingResult bindingResult) {

    StringBuilder errorMessage = new StringBuilder();

    bindingResult.getAllErrors().forEach((error) -> {
      errorMessage.append(error.getDefaultMessage());
    });

    log.error(errorMessage.toString());

    return EnvelopeResponse.builder()
        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
        .message(HttpStatus.BAD_REQUEST.name())
        .build();
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public EnvelopeResponse RuntimeExceptionHandler(RuntimeException e) {

    e.printStackTrace();
    log.error(e.getMessage());

    return EnvelopeResponse.builder()
        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .build();
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public EnvelopeResponse NotFoundExceptionHandler(NotFoundException e) {

    e.printStackTrace();
    log.error(e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public EnvelopeResponse BadRequestExceptionHandler(BadRequestException e) {

    e.printStackTrace();
    log.error(e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public EnvelopeResponse UnauthorizedExceptionHandler(UnauthorizedException e) {

    e.printStackTrace();
    log.error(e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public EnvelopeResponse ForbiddenExceptionHandler(ForbiddenException e) {

    e.printStackTrace();
    log.error(e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(InternalException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public EnvelopeResponse InternalException(InternalException e) {

    log.error(e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }
}
