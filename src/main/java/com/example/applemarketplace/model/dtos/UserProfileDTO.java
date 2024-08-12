package com.example.applemarketplace.model.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserProfileDTO {

    @Size(min = 5, max = 20, message = "{register.size.username}")
    private String username;

    @Size(min = 3, max = 20, message = "{register.size.firstName}")
    private String firstName;

    @Size(min = 3, max = 20, message = "register.size.lastName")
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
