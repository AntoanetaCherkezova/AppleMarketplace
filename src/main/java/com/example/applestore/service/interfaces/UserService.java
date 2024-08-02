package com.example.applestore.service.interfaces;

import com.example.applestore.model.dtos.UserRegisterDTO;
import com.example.applestore.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserRegisterDTO userRegisterDTO);

    void dbInitAdmin();

    Optional<User> findByUsername(String username);

    void saveCurrentUser(User user);
}



