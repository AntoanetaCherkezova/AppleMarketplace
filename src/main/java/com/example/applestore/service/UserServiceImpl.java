package com.example.applestore.service;
import com.example.applestore.config.AdminConfiguration;
import com.example.applestore.model.dtos.UserProfileDTO;
import com.example.applestore.model.dtos.UserRegisterDTO;
import com.example.applestore.model.entity.Contact;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.entity.UserRole;
import com.example.applestore.model.enums.Role;
import com.example.applestore.model.view.UserControlCenterView;
import com.example.applestore.model.view.UserProfileView;
import com.example.applestore.repository.UserRepository;
import com.example.applestore.service.interfaces.UserRoleService;
import com.example.applestore.service.interfaces.UserService;
import com.example.applestore.util.ModelAttributeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        user.setDateOfRegister(LocalDateTime.now());
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
                    .setDateOfRegister(LocalDateTime.now())
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

    @Override
    public UserProfileView mapUserToView(String username) {
        User user = this.userRepository.findByUsername(username).get();
        return this.modelMapper.map(user, UserProfileView.class);
    }

    @Override
    public UserProfileDTO mapUserToDTO(String username) {
        User user = this.userRepository.findByUsername(username).get();
        return this.modelMapper.map(user, UserProfileDTO.class);
    }

    @Override
    public void updateUser(UserProfileDTO userProfileDTO, String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userProfileDTO.getFirstName() != null) user.setFirstName(userProfileDTO.getFirstName());
        if (userProfileDTO.getLastName() != null) user.setLastName(userProfileDTO.getLastName());

        if (userProfileDTO.getContactPhone() != null) user.getContact().setPhone(userProfileDTO.getContactPhone());

        if (userProfileDTO.getCity() != null) user.setCity(userProfileDTO.getCity());

        this.userRepository.save(user);
    }

    @Override
    public List<UserControlCenterView> findAllUsersExcludingMyUser() {
        return userRepository.findAllUsersExcludingUsername("antoaneta")
                .stream()
                .map(user -> {
                    UserControlCenterView view = modelMapper.map(user, UserControlCenterView.class);
                    view.setDateOfRegister(ModelAttributeUtil.formatDate(user.getDateOfRegister()));
                    view.setDeviceCounts(user.getMyIphones().size() + user.getMyMacBooks().size() + user.getMyWatches().size());
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addAdminRole(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserRole adminRole = this.userRoleService.findByRole(Role.ADMIN);
            user.getRoles().add(adminRole);
            this.userRepository.save(user);
        }
    }

    @Override
    public void removeAdminRole(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserRole adminRole = this.userRoleService.findByRole(Role.ADMIN);
            user.getRoles().remove(adminRole);
            this.userRepository.save(user);
        }
    }

    @Override
    public void blockUser(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setBlocked(true);
                    userRepository.save(user);
                });
    }

    @Override
    public void unblockUser(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setBlocked(false);
                    userRepository.save(user);
                });
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    userRepository.deleteById(userId);
                });
    }
}
