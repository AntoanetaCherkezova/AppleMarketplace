package com.example.applemarketplace.service.interfaces;

import com.example.applemarketplace.model.entity.UserRole;
import com.example.applemarketplace.model.enums.Role;

public interface UserRoleService {

    UserRole findByRole(Role role);

    void dbInit();
}

