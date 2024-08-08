package com.example.applestore.model.view;
import com.example.applestore.model.entity.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class UserControlCenterView {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String contactPhone;
    private String contactEmail;
    private boolean isBlocked;
    private String city;
    private String dateOfRegister;
    private int deviceCounts;
    private Set<UserRole> roles;
    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(role -> role.getName().name().equalsIgnoreCase(roleName));
    }
}
