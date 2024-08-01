package com.example.applestore.init;


import com.example.applestore.service.interfaces.UserRoleService;
import com.example.applestore.service.interfaces.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBaseInitServiceImpl implements DataBaseInitService{

    private final UserRoleService userRoleService;
    private final UserService userService;

    @Autowired
    public DataBaseInitServiceImpl(UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    @PostConstruct
    public void init() {
        this.userRoleService.dbInit();
        this.userService.dbInitAdmin();
    }
}
