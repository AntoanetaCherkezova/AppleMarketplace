package com.example.applemarketplace.web;
import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.repository.UserRepository;
import com.example.applemarketplace.repository.UserRoleRepository;
import com.example.applemarketplace.repository.WatchRepository;
import com.example.applemarketplace.service.interfaces.WatchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static com.example.applemarketplace.TestDataUtils.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WatchService watchService;

    @Autowired
    private WatchRepository watchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        watchRepository.deleteAll();
        user = createUser(passwordEncoder, userRoleRepository);
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddWatchForm() throws Exception {
        mockMvc.perform(get("/watches/add-watch"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-watch"))
                .andExpect(model().attributeExists("watchAddDTO"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    public void testProcessAddWatchesFormWithValidData() throws Exception {
        Assertions.assertEquals(0, watchRepository.count());
        MockMultipartFile picture1 = getMockMultipartFile();

        WatchAddDTO watchAddDTO = createWatchAddDTO(picture1);

        mockMvc.perform(multipart("/watches/add-watch")
                        .file(picture1)
                        .param("statusDevice", watchAddDTO.getStatusDevice())
                        .param("warranty", String.valueOf(watchAddDTO.getWarranty()))
                        .param("model", watchAddDTO.getModel())
                        .param("resolution", watchAddDTO.getResolution())
                        .param("processor", watchAddDTO.getProcessor())
                        .param("capacityRam", watchAddDTO.getCapacityRam().name())
                        .param("operatingSystem", watchAddDTO.getOperatingSystem())
                        .param("displaySize", watchAddDTO.getDisplaySize())
                        .param("colour", watchAddDTO.getColour().name())
                        .param("dateOfPurchase", watchAddDTO.getDateOfPurchase().toString())
                        .param("price", watchAddDTO.getPrice().toString())
                        .param("display", watchAddDTO.getDisplay().name())
                        .param("internalMemory", watchAddDTO.getInternalMemory().name())
                        .param("sizes", watchAddDTO.getSizes())
                        .param("touchScreenDisplay", String.valueOf(watchAddDTO.isTouchScreenDisplay()))
                        .param("simCardSupport", String.valueOf(watchAddDTO.isSimCardSupport()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Assertions.assertEquals(1, watchRepository.count());
        Assertions.assertEquals(true, watchService.availableWatches());
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    public void testProcessAddWatchesFormWithInvalidData() throws Exception {
        Assertions.assertEquals(0, watchRepository.count());

        MockMultipartFile invalidPicture = new MockMultipartFile(
                "photosUrls",
                "invalid.jpg",
                "image/jpg",
                new byte[0]);

        WatchAddDTO watchAddDTO = createWrongWatchAddDTO();
        mockMvc.perform(multipart("/watches/add-watch")
                        .file(invalidPicture)
                        .param("statusDevice", watchAddDTO.getStatusDevice())
                        .param("warranty", String.valueOf(watchAddDTO.getWarranty()))
                        .param("model", watchAddDTO.getModel())
                        .param("resolution", watchAddDTO.getResolution())
                        .param("processor", watchAddDTO.getProcessor())
                        .param("capacityRam", watchAddDTO.getCapacityRam() != null ? watchAddDTO.getCapacityRam().name() : "")
                        .param("operatingSystem", watchAddDTO.getOperatingSystem())
                        .param("displaySize", watchAddDTO.getDisplaySize())
                        .param("colour", watchAddDTO.getColour() != null ? watchAddDTO.getColour().name() : "")
                        .param("dateOfPurchase", watchAddDTO.getDateOfPurchase().toString())
                        .param("price", watchAddDTO.getPrice().toString())
                        .param("display", watchAddDTO.getDisplay() != null ? watchAddDTO.getDisplay().name() : "")
                        .param("internalMemory", watchAddDTO.getInternalMemory() != null ? watchAddDTO.getInternalMemory().name() : "")
                        .param("sizes", watchAddDTO.getSizes())
                        .param("touchScreenDisplay", String.valueOf(watchAddDTO.isTouchScreenDisplay()))
                        .param("simCardSupport", String.valueOf(watchAddDTO.isSimCardSupport()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-watch"))
                .andExpect(model().attributeHasFieldErrors("watchAddDTO", "photosUrls", "statusDevice", "warranty", "model", "resolution", "processor", "capacityRam", "operatingSystem", "displaySize", "colour", "dateOfPurchase", "price", "internalMemory", "sizes"));

        Assertions.assertEquals(false, watchService.availableWatches());
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        watchRepository.deleteAll();
    }
}