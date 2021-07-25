package com.example.demo.constraint.annotation;

import com.example.demo.constraint.validator.CustomPasswordValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = CustomPasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Password must contain at least one big letter and one number symbol. " +
            "<br> You can use only english letter." +
            "<br> Password size must be between 6 and 30 characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
