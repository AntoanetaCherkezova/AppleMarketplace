package com.example.applemarketplace.web;
import com.example.applemarketplace.model.dtos.MacBookAddDTO;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.repository.MacBookRepository;
import com.example.applemarketplace.repository.UserRepository;
import com.example.applemarketplace.repository.UserRoleRepository;
import com.example.applemarketplace.service.interfaces.MacBookService;
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
class MacBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MacBookService macBookService;

    @Autowired
    private MacBookRepository macBookRepository;

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
        macBookRepository.deleteAll();
        user = createUser(passwordEncoder, userRoleRepository);
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddMacBookForm() throws Exception {
        mockMvc.perform(get("/macBooks/add-macBook"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-macBook"))
                .andExpect(model().attributeExists("macBookAddDTO"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    public void testProcessAddMacBooksFormWithValidData() throws Exception {
        Assertions.assertEquals(0, macBookRepository.count());
        MockMultipartFile picture1 = getMockMultipartFile();

        MacBookAddDTO macBookAddDTO = createMacBookAddDTO(picture1);

        mockMvc.perform(multipart("/macBooks/add-macBook")
                        .file(picture1)
                        .param("statusDevice", macBookAddDTO.getStatusDevice())
                        .param("warranty", String.valueOf(macBookAddDTO.getWarranty()))
                        .param("model", macBookAddDTO.getModel())
                        .param("resolution", macBookAddDTO.getResolution())
                        .param("processor", macBookAddDTO.getProcessor())
                        .param("capacityRam", macBookAddDTO.getCapacityRam().name())
                        .param("operatingSystem", macBookAddDTO.getOperatingSystem())
                        .param("displaySize", macBookAddDTO.getDisplaySize())
                        .param("colour", macBookAddDTO.getColour().name())
                        .param("dateOfPurchase", macBookAddDTO.getDateOfPurchase().toString())
                        .param("price", macBookAddDTO.getPrice().toString())
                        .param("HDD", String.valueOf(macBookAddDTO.getHDD()))
                        .param("videoCardType", String.valueOf(macBookAddDTO.getVideoCardType()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Assertions.assertEquals(1, macBookRepository.count());
        Assertions.assertEquals(true, macBookService.availableMacBooks());
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUsername", roles = {"USER"})
    public void testProcessAddMacBooksFormWithInvalidData() throws Exception {
        Assertions.assertEquals(0, macBookRepository.count());

        MockMultipartFile invalidPicture = new MockMultipartFile(
                "photosUrls",
                "invalid.jpg",
                "image/jpeg",
                new byte[0]);

        MacBookAddDTO macBookAddDTO = createWrongMacBookAddDTO();
        mockMvc.perform(multipart("/macBooks/add-macBook")
                        .file(invalidPicture)
                        .param("statusDevice", macBookAddDTO.getStatusDevice())
                        .param("warranty", String.valueOf(macBookAddDTO.getWarranty()))
                        .param("model", macBookAddDTO.getModel())
                        .param("resolution", macBookAddDTO.getResolution())
                        .param("processor", macBookAddDTO.getProcessor())
                        .param("capacityRam", macBookAddDTO.getCapacityRam() != null ? macBookAddDTO.getCapacityRam().name() : "")
                        .param("operatingSystem", macBookAddDTO.getOperatingSystem())
                        .param("displaySize", macBookAddDTO.getDisplaySize())
                        .param("colour", macBookAddDTO.getColour() != null ? macBookAddDTO.getColour().name() : "")
                        .param("dateOfPurchase", macBookAddDTO.getDateOfPurchase().toString())
                        .param("price", macBookAddDTO.getPrice().toString())
//                        .param("HDD", String.valueOf(macBookAddDTO.isHDD()))
//                        .param("videoCardType", String.valueOf(macBookAddDTO.isVideoCardType()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-macBook")) // Adjust the view name as needed
                .andExpect(model().attributeHasFieldErrors("macBookAddDTO", "photosUrls", "statusDevice", "warranty", "model", "resolution", "processor", "capacityRam", "operatingSystem", "displaySize", "colour", "dateOfPurchase", "price"));

        Assertions.assertEquals(false, macBookService.availableMacBooks());
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        macBookRepository.deleteAll();
    }
}