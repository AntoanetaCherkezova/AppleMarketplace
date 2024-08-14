package com.example.applemarketplace.web;
import com.example.applemarketplace.model.dtos.IphoneAddDTO;
import com.example.applemarketplace.model.dtos.MacBookAddDTO;
import com.example.applemarketplace.model.dtos.UserProfileDTO;
import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.view.UserProfileView;
import com.example.applemarketplace.repository.*;
import com.example.applemarketplace.service.interfaces.IphoneService;
import com.example.applemarketplace.service.interfaces.MacBookService;
import com.example.applemarketplace.service.interfaces.UserService;
import com.example.applemarketplace.service.interfaces.WatchService;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.io.IOException;
import java.util.ArrayList;
import static com.example.applemarketplace.TestDataUtils.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IphoneService iphoneService;
    @Autowired
    private MacBookService macBookService;
    @Autowired
    private WatchService watchService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private IphoneRepository iphoneRepository;
    @Autowired
    private MacBookRepository macBookRepository;
    @Autowired
    private WatchRepository watchRepository;
    @Mock
    private UserDetailsService userDetailsService;
    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        iphoneRepository.deleteAll();
        macBookRepository.deleteAll();
        watchRepository.deleteAll();
        userRepository.deleteAll();

        user = createUser(passwordEncoder, userRoleRepository);
        userRepository.save(user);

        MockMultipartFile picture1 = getMockMultipartFile();

        userDetails = new org.springframework.security.core.userdetails.User("testUsername", "password", new ArrayList<>());
        when(userDetailsService.loadUserByUsername("testUsername")).thenReturn(userDetails);

        IphoneAddDTO iphoneDTO = createIphoneAddDTO(picture1);
        iphoneService.saveIphone(iphoneDTO, userDetails);

        MacBookAddDTO macBookDTO = createMacBookAddDTO(picture1);
        macBookService.saveMacBook(macBookDTO, userDetails);

        WatchAddDTO watchDTO = createWatchAddDTO(picture1);
        watchService.saveWatch(watchDTO, userDetails);
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowUserProfile() throws Exception {
        UserProfileView userProfileView = this.userService.mapUserToView(userDetails.getUsername());
        UserProfileDTO userProfileDTO = this.userService.mapUserToDTO(userDetails.getUsername());

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("userProfileView"))
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(model().attribute("userProfileView", Matchers.hasProperty("username", Matchers.is(userProfileView.getUsername()))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("firstName", Matchers.is(userProfileDTO.getFirstName()))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testGetUserProfile() throws Exception {
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("userProfileView"))
                .andExpect(model().attributeExists("userProfile"));
    }
}
