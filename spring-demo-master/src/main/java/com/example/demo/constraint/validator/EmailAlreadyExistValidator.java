package com.example.demo.constraint.validator;

import com.example.demo.constraint.annotation.EmailAlreadyExist;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class EmailAlreadyExistValidator implements ConstraintValidator<EmailAlreadyExist, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isUserExist(email);
    }
}
