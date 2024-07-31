package com.example.applestore.validation.confirmPassword;

import com.example.applestore.model.dtos.UserRegisterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordForm, UserRegisterDTO> {
    private String message;

    @Override
    public void initialize(ConfirmPasswordForm constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRegisterDTO userRegistrationDTO, ConstraintValidatorContext context) {
        String password = userRegistrationDTO.getPassword();
        String confirmPassword = userRegistrationDTO.getConfirmPassword();

        if (password != null && confirmPassword != null && password.equals(confirmPassword)) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("confirmPassword")
                .addConstraintViolation();

        return false;
    }
}