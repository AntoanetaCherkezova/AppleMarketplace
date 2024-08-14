package com.example.applemarketplace.web;
import com.example.applemarketplace.model.dtos.IphoneAddDTO;
import com.example.applemarketplace.model.dtos.MacBookAddDTO;
import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.entity.Watch;
import com.example.applemarketplace.repository.*;
import com.example.applemarketplace.service.UserDetailsServiceImpl;
import com.example.applemarketplace.service.interfaces.IphoneService;
import com.example.applemarketplace.service.interfaces.MacBookService;
import com.example.applemarketplace.service.interfaces.WatchService;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import static com.example.applemarketplace.TestDataUtils.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IphoneService iphoneService;
    @Autowired
    private MacBookService macBookService;
    @Autowired
    private WatchService watchService;
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
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        iphoneRepository.deleteAll();
        macBookRepository.deleteAll();
        watchRepository.deleteAll();
        userRepository.deleteAll();

        user = createUser(passwordEncoder, userRoleRepository);
        userRepository.save(user);

        MockMultipartFile picture1 = getMockMultipartFile();

        userDetails = userDetailsService.loadUserByUsername("testUsername");

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
    void testShowAllDevices() throws Exception {
        mockMvc.perform(get("/devices/all")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "id"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "all"))
                .andExpect(view().name("devices"))
                .andExpect(model().attributeExists("devices"))
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(3))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowAllDevicesWithNoDevices() throws Exception {
        cleanUp();
        mockMvc.perform(get("/devices/all")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowIphones() throws Exception {
        mockMvc.perform(get("/devices/iPhones")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "iPhones"))
                .andExpect(view().name("devices"))
                .andExpect(model().attributeExists("devices"))
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowIphonesWithNoIphones() throws Exception {
        cleanUp();
        mockMvc.perform(get("/devices/iPhones")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowMacBooks() throws Exception {
        mockMvc.perform(get("/devices/macBooks")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "macBooks"))
                .andExpect(view().name("devices"))
                .andExpect(model().attributeExists("devices"))
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowMacBooksWithNoMacBooks() throws Exception {
        cleanUp();
        mockMvc.perform(get("/devices/macBooks")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowWatches() throws Exception {
        mockMvc.perform(get("/devices/watches")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "watches"))
                .andExpect(view().name("devices"))
                .andExpect(model().attributeExists("devices"))
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testShowWatchesWithNoWatches() throws Exception {
        cleanUp();
        mockMvc.perform(get("/devices/watches")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("devices", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }


    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testDeviceProfileMacBook() throws Exception {
        Assertions.assertEquals(1, macBookRepository.count());

        mockMvc.perform(get("/devices/device-profile/{type}/{deviceId}","macBook", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("device-profile"))
                .andExpect(model().attribute("deviceType", "macBook"))
                .andExpect(model().attribute("device", Matchers.hasProperty("model", Matchers.is("MacBook Pro 16"))))
                .andExpect(model().attribute("isOwner", Matchers.is(true)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testDeviceProfileWatch() throws Exception {
        Assertions.assertEquals(1, watchRepository.count());

        mockMvc.perform(get("/devices/device-profile/{type}/{deviceId}","watch", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("device-profile"))
                .andExpect(model().attribute("deviceType", "watch"))
                .andExpect(model().attribute("device", Matchers.hasProperty("model", Matchers.is("Apple Watch Series 8"))))
                .andExpect(model().attribute("isOwner", Matchers.is(true)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testDeviceProfileInvalidType() throws Exception {
        mockMvc.perform(get("/devices/device-profile/invalidType/{deviceId}", 1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testDeviceProfileNotFound() throws Exception {
        mockMvc.perform(get("/devices/device-profile/iPhone/{deviceId}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));
    }

    @AfterEach
    void cleanUp() {
        iphoneRepository.deleteAll();
        macBookRepository.deleteAll();
        watchRepository.deleteAll();
        userRepository.deleteAll();
    }

}