package com.example.demo.constraint.annotation;

import com.example.demo.constraint.validator.ExistPublisherValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistPublisherValidator.class)
@Documented
public @interface ExistPublisher {
    String message() default "Publisher isn't exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
