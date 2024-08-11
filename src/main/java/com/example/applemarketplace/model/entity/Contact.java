package com.example.applemarketplace.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity {

    @Column
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;
}

