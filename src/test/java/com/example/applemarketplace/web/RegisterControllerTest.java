package com.example.applemarketplace.web;
import com.example.applemarketplace.model.dtos.UserRegisterDTO;
import com.example.applemarketplace.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRegisterDTO = createUser();
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterDTO"));
    }

    @Test
    void testRegistration() throws Exception {
        Assertions.assertEquals(0, userRepository.count());

        mockMvc.perform(multipart("/user/register")
                        .param("username", userRegisterDTO.getUsername())
                        .param("email", userRegisterDTO.getEmail())
                        .param("firstName", userRegisterDTO.getFirstName())
                        .param("lastName", userRegisterDTO.getLastName())
                        .param("age", String.valueOf(userRegisterDTO.getAge()))
                        .param("password", userRegisterDTO.getPassword())
                        .param("confirmPassword", userRegisterDTO.getConfirmPassword())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Assertions.assertEquals(1, userRepository.count());
        Assertions.assertNotNull(userRepository.findByUsername("testUsername").get());
        Assertions.assertEquals("test@example.com", userRepository.findByUsername("testUsername").get().getContact().getEmail());
    }

    @Test
    @DirtiesContext
    void testRegistrationWithWrongUser() throws Exception {
        Assertions.assertEquals(0, userRepository.count());

        mockMvc.perform(multipart("/user/register")
                        .param("username", "")
                        .param("email", "")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("age", "0")
                        .param("password", "")
                        .param("confirmPassword", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("userRegisterDTO", "username", "email", "firstName", "lastName", "age", "password", "confirmPassword"));

        Assertions.assertEquals(0, userRepository.count());
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    private UserRegisterDTO createUser() {
        UserRegisterDTO registerDTO = new UserRegisterDTO()
                .setUsername("testUsername")
                .setEmail("test@example.com")
                .setFirstName("Test")
                .setLastName("User")
                .setAge(33)
                .setPassword("password123")
                .setConfirmPassword("password123");
        return registerDTO;
    }

}