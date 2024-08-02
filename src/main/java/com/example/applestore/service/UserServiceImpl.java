package com.example.applestore.service;
import com.example.applestore.config.AdminConfiguration;
import com.example.applestore.model.dtos.UserRegisterDTO;
import com.example.applestore.model.entity.Contact;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.entity.UserRole;
import com.example.applestore.model.enums.Role;
import com.example.applestore.repository.UserRepository;
import com.example.applestore.service.interfaces.UserRoleService;
import com.example.applestore.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final AdminConfiguration adminConfiguration;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleService userRoleService, AdminConfiguration adminConfiguration) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.adminConfiguration = adminConfiguration;
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

    @Override
    public void dbInitAdmin() {
        if (this.userRepository.count() == 0) {
            Set<UserRole> roles = new HashSet<>();
            roles.add(this.userRoleService.findByRole(Role.USER));
            roles.add(this.userRoleService.findByRole(Role.ADMIN));

            User user = new User()
                    .setUsername(adminConfiguration.getUsername())
                    .setPassword(passwordEncoder.encode(adminConfiguration.getPassword()))
                    .setFirstName(adminConfiguration.getFirstName())
                    .setLastName(adminConfiguration.getLastName())
                    .setContact(new Contact().setEmail(adminConfiguration.getEmail()).setPhone(adminConfiguration.getPhoneNumber()))
                    .setAge(adminConfiguration.getAge())
                    .setCity(adminConfiguration.getCity())
                    .setRegisteredOn(LocalDateTime.now())
                    .setRoles(roles);
            this.userRepository.save(user);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void saveCurrentUser(User user) {
        this.userRepository.save(user);
    }
}
