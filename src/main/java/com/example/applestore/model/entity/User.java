package com.example.applestore.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Contact contact;

    @Column(nullable = false)
    private int age;

    @Column(name = "is_banned")
    private boolean isBanned;

    @Column
    private String city;

    @Column(nullable = false, name = "registered_on")
    private LocalDateTime registeredOn;

    @Column
    private LocalDateTime modified;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "my_iphones",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "iphone_id")
    )
    private List<Iphone> myIphones;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "my_macBooks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "macBook_id")
    )
    private List<MacBook> myMacBooks;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "my_watches",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "watch_id")
    )
    private List<Watch> myWatches;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    public User() {
        this.myIphones = new ArrayList<>();
        this.myMacBooks = new ArrayList<>();
        this.myWatches = new ArrayList<>();
        this.roles = new LinkedHashSet<>();
    }
}