package com.example.applemarketplace.web;
import com.example.applemarketplace.model.dtos.ContactDTO;
import com.example.applemarketplace.service.interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.mockito.Mockito.*;

class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ViewResolver viewResolver = new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
        this.mockMvc = MockMvcBuilders.standaloneSetup(contactController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testShowContactForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact-us"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("contact-us"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("contactDTO"));
    }

    @Test
    void testSendMessageWithErrors() throws Exception {
        ContactDTO contactDTO = new ContactDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/contact-us/send-message")
                        .flashAttr("contactDTO", contactDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/contact-us"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("error"));
    }

    @Test
    void testSendMessageSuccess() throws Exception {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setFullName("John Doe");
        contactDTO.setEmail("john.doe@example.com");
        contactDTO.setSubject("Test Subject");
        contactDTO.setMessage("Test Message");

        mockMvc.perform(MockMvcRequestBuilders.post("/contact-us/send-message")
                        .flashAttr("contactDTO", contactDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/contact-us"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("success"));

        verify(emailService, times(1)).sendEmailFromUser(contactDTO.getSubject(), "Name: John Doe\nEmail: john.doe@example.com\nSubject: Test Subject\nMessage: Test Message");
    }
}
