package com.example.applestore.validation.photosValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhotosValidator implements ConstraintValidator<ValidPhotos, List<MultipartFile>> {

    private String message;

    @Override
    public void initialize(ValidPhotos constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
                return true;
            }
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
