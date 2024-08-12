package com.example.applemarketplace.model.dtos;

import com.example.applemarketplace.validation.confirmPassword.ConfirmPasswordForm;
import com.example.applemarketplace.validation.email.UniqueEmail;
import com.example.applemarketplace.validation.uniqueUsername.UniqueUsername;
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
    @Size(min = 5, max = 20, message = "{register.size.username}")
    private String username;

    @Size(min = 3, max = 20, message = "{register.size.password}")
    private String password;

    @Size(min = 3, max = 20, message = "{register.size.password}")
    private String confirmPassword;

    @Size(min = 3, max = 20, message = "{register.size.firstName}")
    private String firstName;

    @Size(min = 3, max = 20, message = "{register.size.lastName}")
    private String lastName;

    @UniqueEmail
    @Email(message = "{register.email.invalid}")
    @NotBlank(message = "{register.email.notBlank}")
    private String email;

    @Min(value = 14, message = "{register.age.min}")
    private int age;

}
