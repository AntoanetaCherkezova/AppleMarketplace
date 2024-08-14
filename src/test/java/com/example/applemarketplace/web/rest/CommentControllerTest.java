package com.example.applemarketplace.web.rest;
import com.example.applemarketplace.model.dtos.CommentAddDTO;
import com.example.applemarketplace.model.dtos.IphoneAddDTO;
import com.example.applemarketplace.model.dtos.MacBookAddDTO;
import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.model.entity.Comment;
import com.example.applemarketplace.model.entity.Iphone;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.repository.*;
import com.example.applemarketplace.service.UserDetailsServiceImpl;
import com.example.applemarketplace.service.interfaces.IphoneService;
import com.example.applemarketplace.service.interfaces.MacBookService;
import com.example.applemarketplace.service.interfaces.WatchService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.applemarketplace.TestDataUtils.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CommentControllerTest {

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
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testGetCommentsForIphone() throws Exception {
        Comment comment = new Comment();
        comment.setComment("Good device iPhone!");
        comment.setCommentedAt(LocalDateTime.now());
        comment.setUser(user);

        Iphone iphone = iphoneRepository.findById(1L).orElseThrow();
        iphone.getComments().add(comment);
        iphoneRepository.save(iphone);

        mockMvc.perform(get("/comments/{type}/{deviceId}","iPhone",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Good device iPhone!"));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testCreateCommentForIphone() throws Exception {
        CommentAddDTO commentDto = createCommentAddDTOForIphone();

        mockMvc.perform(post("/comments/add-comment/{type}/{deviceId}", "iPhone", 1L)
                        .contentType("application/x-www-form-urlencoded")
                        .param("comment", commentDto.getComment())
                        .param("user", commentDto.getUser())
                        .param("commentedAt", commentDto.getCommentedAt().toString())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/devices/device-profile/iPhone/1"));

        mockMvc.perform(get("/comments/{type}/{deviceId}", "iPhone", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("High price for this iPhone."));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testCreateCommentForMacBook() throws Exception {
        CommentAddDTO commentDto = createCommentAddDTOForMacBook();

        mockMvc.perform(post("/comments/add-comment/{type}/{deviceId}", "macBook", 1L)
                        .contentType("application/x-www-form-urlencoded")
                        .param("comment", commentDto.getComment())
                        .param("user", commentDto.getUser())
                        .param("commentedAt", commentDto.getCommentedAt().toString())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/devices/device-profile/macBook/1"));

        mockMvc.perform(get("/comments/{type}/{deviceId}", "macBook", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("High price for this MacBook."));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testCreateCommentForWatch() throws Exception {
        CommentAddDTO commentDto = createCommentAddDTOForWatch();

        mockMvc.perform(post("/comments/add-comment/{type}/{deviceId}", "watch", 1L)
                        .contentType("application/x-www-form-urlencoded")
                        .param("comment", commentDto.getComment())
                        .param("user", commentDto.getUser())
                        .param("commentedAt", commentDto.getCommentedAt().toString())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/devices/device-profile/watch/1"));

        mockMvc.perform(get("/comments/{type}/{deviceId}", "watch", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("High price for this Watch."));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testGetCommentsForInvalidDevice() throws Exception {
        mockMvc.perform(get("/comments/{type}/{deviceId}", "iPhone", 999L))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

}