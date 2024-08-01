package com.example.applestore.service.interfaces;

import com.example.applestore.model.dtos.UserRegisterDTO;

public interface UserService {
    void saveUser(UserRegisterDTO userRegisterDTO);

    void dbInitAdmin();
}

