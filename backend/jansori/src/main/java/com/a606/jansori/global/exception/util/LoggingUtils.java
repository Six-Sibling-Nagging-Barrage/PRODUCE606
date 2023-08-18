package com.a606.jansori.global.exception.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class LoggingUtils {

  public static void error(RuntimeException exception){
    String exceptionType = exception.getClass().getSimpleName();
    String message = getExceptionMessage(exceptionType, exception.getMessage());
    StackTraceElement[] stackTraceElement = exception.getStackTrace();

    log.error(message, stackTraceElement[0]);
  }

  public static String getExceptionMessage(String exceptionType, String message){
    if(StringUtils.hasText(message)){
      return exceptionType + " : " + message + "\n \t {}";
    }
    return exceptionType + "\n \t {}";
  }

}
