package com.example.applestore.service.interfaces;

import com.example.applestore.model.entity.UserRole;
import com.example.applestore.model.enums.Role;

public interface UserRoleService {

    UserRole findByRole(Role role);
}
