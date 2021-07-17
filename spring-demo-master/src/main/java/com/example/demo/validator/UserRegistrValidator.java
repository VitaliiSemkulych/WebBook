package com.example.demo.validator;

import com.example.demo.dto.UserRegisterForm;
import com.example.demo.dao.UserRegistrFormDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//validator which process validation user registration field form
@Service
public class UserRegistrValidator implements Validator {


    @Autowired
    private UserRegistrFormDAO userRegistrFormDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserRegisterForm user = (UserRegisterForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephoneNumber", "NotEmpty.appUserForm.telephoneNumber");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");

        if (!errors.hasFieldErrors("userEmail")) {
            if (userRegistrFormDAO.findUserAccount(user.getUserEmail()) != null) {
                errors.rejectValue("userEmail", "Duplicate.appUserForm.email");
            }
        }

        if (!errors.hasFieldErrors("password")) {
            boolean isErrorOccurred = false;
            boolean isNumberContains = false;
            boolean isBigLetterContains = false;
            //check if password contains only number or big and small english letter
            //noinspection Duplicates
            for (int i = 0; i < user.getPassword().length(); i++) {
                char c = user.getPassword().charAt(i);
                if ((int) c < 48 || ((int) c > 57 && (int) c < 65) || ((int) c > 90 && (int) c < 97) || (int) c > 122) {
                    isErrorOccurred = true;
                }
                if ((int) c >= 48 && (int) c <= 57) {
                    isNumberContains = true;
                }
                if ((int) c >= 65 && (int) c <= 90) {
                    isBigLetterContains = true;
                }
            }
            if (!isBigLetterContains || !isNumberContains || isErrorOccurred) {

                errors.rejectValue("password", "Match.appUserForm.password");

            }
        }


    }
}
