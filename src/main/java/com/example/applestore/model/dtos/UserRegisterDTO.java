package com.example.applestore.model.dtos;

import com.example.applestore.validation.confirmPassword.ConfirmPasswordForm;
import com.example.applestore.validation.email.UniqueEmail;
import com.example.applestore.validation.uniqueUsername.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@ConfirmPasswordForm
public class UserRegisterDTO {

    @UniqueUsername
    @Size(min = 5, max = 20, message = "Username length must be between 5 and 20 characters!")
    private String username;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String confirmPassword;

    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;

    @Size(min = 3, max = 20, message = "Last name length must be between 3 and 20 characters!")
    private String lastName;

    @UniqueEmail
    @Email(message = "Invalid email!")
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @Min(value = 14, message = "Age must be at least 14!")
    private int age;

}
