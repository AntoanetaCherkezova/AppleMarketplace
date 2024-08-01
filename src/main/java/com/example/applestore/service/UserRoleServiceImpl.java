package com.example.applestore.service;
import com.example.applestore.model.entity.UserRole;
import com.example.applestore.model.enums.Role;
import com.example.applestore.repository.UserRoleRepository;
import com.example.applestore.service.interfaces.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public void dbInit() {
        if (userRoleRepository.count() == 0) {
            List<UserRole> roles = Arrays.asList(
                    new UserRole(Role.USER),
                    new UserRole(Role.ADMIN));
            this.userRoleRepository.saveAllAndFlush(roles);
        }
    }
}
