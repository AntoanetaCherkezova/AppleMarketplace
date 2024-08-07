package com.example.applestore.model.view;
import com.example.applestore.model.entity.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class UserProfileView {

    private String username;
    private String firstName;
    private String lastName;
    private String contactPhone;
    private String contactEmail;
    private String city;
    private Set<UserRole> roles;
}
