package com.example.applestore.model.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserEmailDTO {
    private String firstName;
    private String lastName;
    private String contactEmail;
}
