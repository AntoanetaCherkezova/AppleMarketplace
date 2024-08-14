package com.example.applemarketplace.web;

import com.example.applemarketplace.model.dtos.IphoneAddDTO;
import com.example.applemarketplace.model.dtos.MacBookAddDTO;
import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.view.DeviceView;
import com.example.applemarketplace.model.view.LatestModelDeviceView;
import com.example.applemarketplace.model.view.ModelsWithLargestMemoryView;
import com.example.applemarketplace.repository.IphoneRepository;
import com.example.applemarketplace.repository.MacBookRepository;
import com.example.applemarketplace.repository.WatchRepository;
import com.example.applemarketplace.repository.UserRepository;
import com.example.applemarketplace.repository.UserRoleRepository;
import com.example.applemarketplace.service.UserDetailsServiceImpl;
import com.example.applemarketplace.service.interfaces.IphoneService;
import com.example.applemarketplace.service.interfaces.MacBookService;
import com.example.applemarketplace.service.interfaces.WatchService;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.applemarketplace.TestDataUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IphoneRepository iphoneRepository;
    @Autowired
    private MacBookRepository macBookRepository;
    @Autowired
    private WatchRepository watchRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IphoneService iphoneService;
    @Autowired
    private MacBookService macBookService;
    @Autowired
    private WatchService watchService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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

        for (int i = 0; i < 2; i++) {
            IphoneAddDTO iphoneDTO = createIphoneAddDTO(picture1, BigDecimal.valueOf(1000 + i * 1000), 64 + i * 64);
            iphoneService.saveIphone(iphoneDTO, userDetails);

            MacBookAddDTO macBookDTO = createMacBookAddDTO(picture1, BigDecimal.valueOf(1000 + i * 1000), 16 + i * 16);
            macBookService.saveMacBook(macBookDTO, userDetails);

            WatchAddDTO watchDTO = createWatchAddDTO(picture1, BigDecimal.valueOf(1000 + i * 1000), 32 + i * 32);
            watchService.saveWatch(watchDTO, userDetails);
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testHome() throws Exception {
        ModelsWithLargestMemoryView iphoneWithLargestMemoryView = iphoneService.iphoneWithLargestMemory();
        LatestModelDeviceView latestModelIphoneView = iphoneService.latestModelIphone();
        LatestModelDeviceView latestModelMacBookView = macBookService.latestModelMacBook();
        LatestModelDeviceView latestModelWatchView = watchService.latestModelWatch();

        List<DeviceView> deviceWithTheLongestWarranty = Stream.concat(
                        Stream.concat(
                                iphoneService.findLongestWarrantyIphone().stream(),
                                macBookService.findLongestWarrantyMacBook().stream()
                        ),
                        watchService.findLongestWarrantyWatch().stream())
                .sorted((d1, d2) -> Integer.compare(d2.getWarranty(), (d1.getWarranty())))
                .limit(5)
                .toList();

        Assertions.assertEquals("2000", iphoneService.findById(2L).getPrice().toString());
        Assertions.assertEquals("2000", macBookService.findById(2L).getPrice().toString());
        Assertions.assertEquals("2000", watchService.findById(2L).getPrice().toString());
        Assertions.assertEquals(5, deviceWithTheLongestWarranty.size());
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists(
                        "availableIPhones",
                        "availableMacBooks",
                        "availableWatches",
                        "iPhoneWithLargestMemoryView",
                        "latestModelIphoneView",
                        "latestModelMacBookView",
                        "latestModelWatchView",
                        "deviceWithTheLongestWarranty"))
                .andExpect(model().attribute("iPhoneWithLargestMemoryView", Matchers.hasProperty("internalMemory", Matchers.is(iphoneWithLargestMemoryView.getInternalMemory()))))
                .andExpect(model().attribute("latestModelIphoneView", Matchers.hasProperty("model", Matchers.is(latestModelIphoneView.getModel()))))
                .andExpect(model().attribute("latestModelMacBookView", Matchers.hasProperty("model", Matchers.is(latestModelMacBookView.getModel()))))
                .andExpect(model().attribute("latestModelWatchView", Matchers.hasProperty("model", Matchers.is(latestModelWatchView.getModel()))));
    }

    @AfterEach
    void cleanUp() {
        iphoneRepository.deleteAll();
        macBookRepository.deleteAll();
        watchRepository.deleteAll();
        userRepository.deleteAll();
    }
}
