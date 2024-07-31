package com.example.applestore.service;
import com.example.applestore.model.entity.UserRole;
import com.example.applestore.model.enums.Role;
import com.example.applestore.repository.UserRoleRepository;
import com.example.applestore.service.interfaces.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public UserRole findByRole(Role role) {
        return userRoleRepository.findByName(role);
    }
}
