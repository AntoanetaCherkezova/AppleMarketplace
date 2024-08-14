package com.example.applemarketplace.service;

import com.example.applemarketplace.config.AdminConfiguration;
import com.example.applemarketplace.model.dtos.UserEmailDTO;
import com.example.applemarketplace.model.dtos.UserProfileDTO;
import com.example.applemarketplace.model.dtos.UserRegisterDTO;
import com.example.applemarketplace.model.entity.*;
import com.example.applemarketplace.model.enums.Role;
import com.example.applemarketplace.model.view.UserControlCenterView;
import com.example.applemarketplace.model.view.UserProfileView;
import com.example.applemarketplace.repository.UserRepository;
import com.example.applemarketplace.service.interfaces.UserRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private AdminConfiguration adminConfiguration;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private UserRegisterDTO userRegisterDTO;
    private UserProfileDTO userProfileDTO;
    private User user;
    private UserRole adminRole;
    private UserRole userRole;
    private MultipartFile picture;

    @BeforeEach
    void setUp() {
        userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUsername");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setFirstName("Antoniq");
        userRegisterDTO.setLastName("Bratanova");
        userRegisterDTO.setAge(33);

        userProfileDTO = new UserProfileDTO();
        userProfileDTO.setFirstName("Antoniq");
        userProfileDTO.setLastName("Bratanova");
        userProfileDTO.setContactPhone("123456789");
        userProfileDTO.setCity("City");

        user = new User();
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");
        user.setDateOfRegister(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setContact(new Contact());

        adminRole = new UserRole(Role.ADMIN);

        userRole = new UserRole(Role.USER);
    }

    @Test
    void saveUser() {
        when(modelMapper.map(userRegisterDTO, User.class)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRoleService.findByRole(Role.USER)).thenReturn(userRole);

        userService.saveUser(userRegisterDTO);

        verify(userRepository).saveAndFlush(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("testUsername", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("test@example.com", savedUser.getContact().getEmail());
        assertTrue(savedUser.getRoles().contains(userRole));
    }

    @Test
    void dbInitAdmin() {
        when(adminConfiguration.getUsername()).thenReturn("admin");
        when(adminConfiguration.getPassword()).thenReturn("adminPass");
        when(adminConfiguration.getFirstName()).thenReturn("Admin");
        when(adminConfiguration.getLastName()).thenReturn("User");
        when(adminConfiguration.getEmail()).thenReturn("admin@example.com");
        when(adminConfiguration.getPhoneNumber()).thenReturn("123456789");
        when(adminConfiguration.getAge()).thenReturn(30);
        when(adminConfiguration.getCity()).thenReturn("AdminCity");
        when(userRepository.count()).thenReturn(0L);
        when(userRoleService.findByRole(Role.ADMIN)).thenReturn(adminRole);
        when(userRoleService.findByRole(Role.USER)).thenReturn(userRole);
        when(passwordEncoder.encode("adminPass")).thenReturn("adminPass");

        userService.dbInitAdmin();

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("admin", savedUser.getUsername());
        assertTrue(savedUser.getRoles().contains(adminRole));
        assertTrue(savedUser.getRoles().contains(userRole));
        assertEquals("adminPass", savedUser.getPassword());
        assertEquals("Admin", savedUser.getFirstName());
        assertEquals("User", savedUser.getLastName());
        assertEquals("admin@example.com", savedUser.getContact().getEmail());
        assertEquals("123456789", savedUser.getContact().getPhone());
        assertEquals(30, savedUser.getAge());
        assertEquals("AdminCity", savedUser.getCity());
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("testUsername");

        assertTrue(result.isPresent());
        assertEquals("testUsername", result.get().getUsername());
    }

    @Test
    void saveCurrentUser() {
        userService.saveCurrentUser(user);

        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals(user.getUsername(), capturedUser.getUsername());
    }

    @Test
    void findAll() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("testUsername", result.get(0).getUsername());
    }

    @Test
    void mapUserToDTO() {
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        when(modelMapper.map(user, UserProfileDTO.class)).thenReturn(userProfileDTO);

        UserProfileDTO result = userService.mapUserToDTO("testUsername");

        assertNotNull(result);
        assertEquals(userProfileDTO, result);
    }

    @Test
    void mapUserToView() {
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        UserProfileView userProfileView = new UserProfileView();
        when(modelMapper.map(user, UserProfileView.class)).thenReturn(userProfileView);

        UserProfileView result = userService.mapUserToView("testUsername");

        assertNotNull(result);
        assertEquals(userProfileView, result);
    }

//    @Test
//    void updateUser() {
//        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
//        UserProfileDTO updateDTO = new UserProfileDTO();
//        updateDTO.setFirstName("Toni");
//        updateDTO.setContactPhone("987654321");
//        updateDTO.setCity("NewCity");
//
//
//        userService.updateUser(updateDTO, "testUsername");
//
//        verify(userRepository).save(userCaptor.capture());
//
//        User updatedUser = userCaptor.getValue();
//        assertEquals("Toni", updatedUser.getFirstName());
//        assertEquals("987654321", updatedUser.getContact().getPhone());
//        assertEquals("NewCity", updatedUser.getCity());
//    }

    @Test
    void findAllUsersAndAdminsExceptConfiguredAdmin() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setDateOfRegister(LocalDateTime.now().minusDays(1));
        User user2 = new User();
        user2.setUsername(adminConfiguration.getUsername());
        user2.setDateOfRegister(LocalDateTime.now());

        when(userRepository.findAllUsersExcludingUsername(adminConfiguration.getUsername())).thenReturn(Arrays.asList(user1));
        when(modelMapper.map(user1, UserControlCenterView.class)).thenReturn(new UserControlCenterView());

        List<UserControlCenterView> result = userService.findAllUsersExcludingMyUser();

        assertEquals(1, result.size());
    }

    @Test
    void addAdminRole() {
        User user = new User();
        UserRole adminRole = new UserRole(Role.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRoleService.findByRole(Role.ADMIN)).thenReturn(adminRole);

        userService.addAdminRole(1L);

        verify(userRepository).findById(1L);
        verify(userRoleService).findByRole(Role.ADMIN);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertTrue(savedUser.getRoles().contains(adminRole));
    }

    @Test
    void removeAdminRole() {
        User user = new User();
        UserRole adminRole = new UserRole(Role.ADMIN);
        user.getRoles().add(adminRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRoleService.findByRole(Role.ADMIN)).thenReturn(adminRole);

        userService.removeAdminRole(1L);

        verify(userRepository).findById(1L);
        verify(userRoleService).findByRole(Role.ADMIN);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertFalse(savedUser.getRoles().contains(adminRole));
    }

    @Test
    void blockUser() {
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.blockUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertTrue(savedUser.isBlocked());
    }

    @Test
    void unblockUser() {
        User user = new User();
        user.setBlocked(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.unblockUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertFalse(savedUser.isBlocked());
    }


    @Test
    void deleteUser() {
        Iphone iphone = new Iphone();
        MacBook macBook = new MacBook();
        Watch watch = new Watch();

        user.setMyIphones(Collections.singletonList(iphone));
        user.setMyMacBooks(Collections.singletonList(macBook));
        user.setMyWatches(Collections.singletonList(watch));

        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        lenient().doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).deleteById(1L);
    }


    @Test
    void getAllUserEmails() {
        user.getContact().setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(modelMapper.map(user, UserEmailDTO.class)).thenReturn(new UserEmailDTO().setContactEmail("test@example.com"));

        List<UserEmailDTO> result = userService.getAllUserEmails();

        verify(userRepository).findAll();
        verify(modelMapper).map(user, UserEmailDTO.class);

        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getContactEmail());
    }
}