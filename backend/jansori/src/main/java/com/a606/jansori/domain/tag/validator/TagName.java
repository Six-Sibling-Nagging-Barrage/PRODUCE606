package com.a606.jansori.domain.tag.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = TagNameValidator.class)
public @interface TagName {

  String message() default "잔소리의 내용은 2자이상 150자 이내여야 합니다.";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
