package com.a606.jansori.domain.tag.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagNameValidator implements ConstraintValidator<TagName, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    String regex = "^(?!.*\\s{2})\\S{2,8}$";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);

    return matcher.matches();
  }
}
