package com.running.tracker.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Pattern(regexp = "^[A-Z a-z-']*$", message = "Invalid name")
@Documented
@Constraint(validatedBy = {})
public @interface NameEnglish {

  String message() default "Invalid name";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

