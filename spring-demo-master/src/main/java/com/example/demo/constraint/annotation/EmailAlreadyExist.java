package com.example.demo.constraint.annotation;

import com.example.demo.constraint.validator.EmailAlreadyExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailAlreadyExistValidator.class)
@Documented
public @interface EmailAlreadyExist {
    String message() default "User with submitted already exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
