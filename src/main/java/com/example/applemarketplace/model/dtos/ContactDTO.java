package com.example.applemarketplace.model.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ContactDTO {

    @NotBlank(message = "{contact.fullNameError}")
    private String fullName;

    @NotBlank(message = "{contact.emailError1}")
    @Email(message = "{contact.emailError2}")
    private String email;

    @NotBlank(message = "{contact.subjectError}")
    private String subject;

    @NotBlank(message = "{contact.messageError}")
    private String message;

    public ContactDTO() {}

    public ContactDTO(String fullName, String email, String subject, String message) {
        this.fullName = fullName;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }
}
