package com.example.applestore.model.dtos;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ContactDTO {

    private String fullName;
    private String email;
    private String subject;
    private String message;

    public ContactDTO() {}

    public ContactDTO(String fullName, String email, String subject, String message) {
        this.fullName = fullName;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }
}
