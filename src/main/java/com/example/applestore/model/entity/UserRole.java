package com.example.applestore.model.entity;

import com.example.applestore.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role name;

    public UserRole(Role name) {
        this.name = name;
    }
}