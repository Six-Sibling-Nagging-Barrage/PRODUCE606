package com.a606.jansori.domain.nag.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NagContentValidator implements ConstraintValidator<NagContent, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value.trim().length() == 0) {
      return false;
    }
    return value.length() >= 2 && value.length() <= 150;
  }
}
