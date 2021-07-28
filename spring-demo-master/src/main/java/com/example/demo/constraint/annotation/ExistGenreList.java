package com.example.demo.constraint.annotation;

import com.example.demo.constraint.validator.ExistGenreListValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistGenreListValidator.class)
@Documented
public @interface ExistGenreList {
    String message() default "Some entered genres isn't exist in the system.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
