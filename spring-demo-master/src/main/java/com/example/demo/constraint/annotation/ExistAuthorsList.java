package com.example.demo.constraint.annotation;

import com.example.demo.constraint.validator.ExistAuthorsListValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistAuthorsListValidator.class)
@Documented
public @interface ExistAuthorsList {
    String message() default "Some entered authors isn't exist in the system.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
