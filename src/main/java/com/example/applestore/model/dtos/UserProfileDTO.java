package com.example.applestore.model.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserProfileDTO {

    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;

    @Size(min = 3, max = 20, message = "Last name length must be between 3 and 20 characters!")
    private String lastName;

    private String contactPhone;
    private String city;


    public void setContactPhone(String contactPhone) {
        this.contactPhone = isEmpty(contactPhone) ? "" : contactPhone;
    }

    public void setCity(String city) {
        this.city = isEmpty(city) ? "" : city;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
