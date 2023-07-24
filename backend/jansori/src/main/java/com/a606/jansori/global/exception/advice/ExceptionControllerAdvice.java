package com.a606.jansori.global.exception.advice;

import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.global.exception.BadRequestException;
import com.a606.jansori.global.exception.ForbiddenException;
import com.a606.jansori.global.exception.NotFoundException;
import com.a606.jansori.global.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EnvelopeResponse BadRequestExceptionHandler(BindingResult bindingResult) {

        StringBuilder errorMessage = new StringBuilder();

        bindingResult.getAllErrors().forEach((error) -> {
            errorMessage.append(error.getDefaultMessage());
        });

        log.error(errorMessage.toString());

        return EnvelopeResponse.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(errorMessage.toString())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public EnvelopeResponse RuntimeExceptionHandler(RuntimeException e) {

        log.error(e.getStackTrace().toString());

        return EnvelopeResponse.builder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EnvelopeResponse NotFoundExceptionHandler(NotFoundException e) {

        log.error(e.getStackTrace().toString());

        return EnvelopeResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EnvelopeResponse BadRequestExceptionHandler(BadRequestException e) {

        log.error(e.getStackTrace().toString());

        return EnvelopeResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public EnvelopeResponse UnauthorizedExceptionHandler(UnauthorizedException e) {

        log.error(e.getStackTrace().toString());

        return EnvelopeResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public EnvelopeResponse ForbiddenExceptionHandler(ForbiddenException e) {

        log.error(e.getStackTrace().toString());

        return EnvelopeResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }
}
