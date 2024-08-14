package com.example.applemarketplace.web;

import com.example.applemarketplace.model.dtos.IphoneAddDTO;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.repository.IphoneRepository;
import com.example.applemarketplace.repository.UserRepository;
import com.example.applemarketplace.repository.UserRoleRepository;
import com.example.applemarketplace.service.interfaces.IphoneService;
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
class IphoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IphoneService iphoneService;

    @Autowired
    private IphoneRepository iphoneRepository;

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
        iphoneRepository.deleteAll();
        user = createUser(passwordEncoder, userRoleRepository);
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddIphoneForm() throws Exception {
        mockMvc.perform(get("/iPhones/add-iPhone"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-iPhone"))
                .andExpect(model().attributeExists("iPhoneAddDTO"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    public void testProcessAddIphonesFormWithValidData() throws Exception {
        Assertions.assertEquals(0, iphoneRepository.count());
        MockMultipartFile picture1 = getMockMultipartFile();

        IphoneAddDTO iphoneAddDTO = createIphoneAddDTO(picture1);

        mockMvc.perform(multipart("/iPhones/add-iPhone")
                        .file(picture1)
                        .param("statusDevice", iphoneAddDTO.getStatusDevice())
                        .param("warranty", String.valueOf(iphoneAddDTO.getWarranty()))
                        .param("model", iphoneAddDTO.getModel())
                        .param("resolution", iphoneAddDTO.getResolution())
                        .param("processor", iphoneAddDTO.getProcessor())
                        .param("capacityRam", iphoneAddDTO.getCapacityRam().name()) // Assuming CapacityRam is an enum
                        .param("operatingSystem", iphoneAddDTO.getOperatingSystem())
                        .param("displaySize", iphoneAddDTO.getDisplaySize())
                        .param("colour", iphoneAddDTO.getColour().name()) // Assuming Colour is an enum
                        .param("dateOfPurchase", iphoneAddDTO.getDateOfPurchase().toString()) // Ensure proper format if needed
                        .param("price", iphoneAddDTO.getPrice().toString())
                        .param("display", iphoneAddDTO.getDisplay().name())
                        .param("internalMemory", iphoneAddDTO.getInternalMemory().name())
                        .param("battery", iphoneAddDTO.getBattery().name())
                        .param("sizes", iphoneAddDTO.getSizes())
                        .param("dualSim", String.valueOf(iphoneAddDTO.isDualSim()))
                        .param("faceRecognitionSensor", String.valueOf(iphoneAddDTO.isFaceRecognitionSensor()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Assertions.assertEquals(1, iphoneRepository.count());
        Assertions.assertEquals(true, iphoneService.availableIPhones());
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    public void testProcessAddIphonesFormWithInvalidData() throws Exception {
        Assertions.assertEquals(0, iphoneRepository.count());

        MockMultipartFile invalidPicture = new MockMultipartFile(
                "photosUrls",
                "invalid.jpg",
                "image/jpeg",
                new byte[0]);

        IphoneAddDTO iphoneAddDTO = createWrongIphoneAddDTO();
        mockMvc.perform(multipart("/iPhones/add-iPhone")
                        .file(invalidPicture)
                        .param("statusDevice", iphoneAddDTO.getStatusDevice())
                        .param("warranty", String.valueOf(iphoneAddDTO.getWarranty()))
                        .param("model", iphoneAddDTO.getModel())
                        .param("resolution", iphoneAddDTO.getResolution())
                        .param("processor", iphoneAddDTO.getProcessor())
                        .param("capacityRam", iphoneAddDTO.getCapacityRam() != null ? iphoneAddDTO.getCapacityRam().name() : "")
                        .param("operatingSystem", iphoneAddDTO.getOperatingSystem())
                        .param("displaySize", iphoneAddDTO.getDisplaySize())
                        .param("colour", iphoneAddDTO.getColour() != null ? iphoneAddDTO.getColour().name() : "")
                        .param("dateOfPurchase", iphoneAddDTO.getDateOfPurchase().toString())
                        .param("price", iphoneAddDTO.getPrice().toString())
                        .param("display", iphoneAddDTO.getDisplay() != null ? iphoneAddDTO.getDisplay().name() : "")
                        .param("internalMemory", iphoneAddDTO.getInternalMemory() != null ? iphoneAddDTO.getInternalMemory().name() : "")
                        .param("battery", iphoneAddDTO.getBattery() != null ? iphoneAddDTO.getBattery().name() : "")
                        .param("sizes", iphoneAddDTO.getSizes())
                        .param("dualSim", String.valueOf(iphoneAddDTO.isDualSim()))
                        .param("faceRecognitionSensor", String.valueOf(iphoneAddDTO.isFaceRecognitionSensor()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-iPhone")) // Adjust the view name as needed
                .andExpect(model().attributeHasFieldErrors("iphoneAddDTO", "photosUrls", "statusDevice", "warranty", "model", "resolution", "processor", "capacityRam", "operatingSystem", "displaySize", "colour", "dateOfPurchase", "price", "internalMemory", "battery", "sizes"));

        Assertions.assertEquals(false, iphoneService.availableIPhones());
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        iphoneRepository.deleteAll();
    }
}