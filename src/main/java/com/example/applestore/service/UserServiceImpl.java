package com.example.applestore.service;
import com.example.applestore.model.dtos.UserRegisterDTO;
import com.example.applestore.model.entity.Contact;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.enums.Role;
import com.example.applestore.repository.UserRepository;
import com.example.applestore.service.interfaces.UserRoleService;
import com.example.applestore.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    @Override
    public void saveUser(UserRegisterDTO userRegisterDTO) {
        User user = this.modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRegisteredOn(LocalDateTime.now());
        user.getRoles().add(this.userRoleService.findByRole(Role.USER));
        user.setContact(new Contact()
                .setEmail(userRegisterDTO.getEmail()));
        this.userRepository.saveAndFlush(user);
    }
}
