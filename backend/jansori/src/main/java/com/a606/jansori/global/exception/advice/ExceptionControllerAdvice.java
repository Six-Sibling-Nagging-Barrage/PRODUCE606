package com.a606.jansori.global.exception.advice;

import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.global.exception.domain.BadRequestException;
import com.a606.jansori.global.exception.domain.BusinessException;
import com.a606.jansori.global.exception.domain.ForbiddenException;
import com.a606.jansori.global.exception.domain.InternalException;
import com.a606.jansori.global.exception.domain.NotFoundException;
import com.a606.jansori.global.exception.domain.UnauthorizedException;
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

    log.error("{} : {}", e.getClass().getName(), e.getMessage());

    return EnvelopeResponse.builder()
        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .build();
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public EnvelopeResponse NotFoundExceptionHandler(NotFoundException e) {

    log.error("{} : {}", e.getClass().getName(), e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public EnvelopeResponse BadRequestExceptionHandler(BadRequestException e) {

    log.error("{} : {}", e.getClass().getName(), e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public EnvelopeResponse UnauthorizedExceptionHandler(UnauthorizedException e) {

    log.error("{} : {}", e.getClass().getName(), e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public EnvelopeResponse ForbiddenExceptionHandler(ForbiddenException e) {

    log.error("{} : {}", e.getClass().getName(), e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(InternalException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public EnvelopeResponse InternalException(InternalException e) {

    log.error("{} : {}", e.getClass().getName(), e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public EnvelopeResponse BusinessExceptionHandler(BusinessException e) {

    log.error("{} : {}", e.getClass().getName(), e.getMessage());

    return EnvelopeResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();
  }
}
