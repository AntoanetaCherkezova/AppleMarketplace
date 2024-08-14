package com.example.applemarketplace.scheduler;
import com.example.applemarketplace.model.dtos.UserEmailDTO;
import com.example.applemarketplace.service.interfaces.EmailService;
import com.example.applemarketplace.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyProductUpdateSchedulerTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private WeeklyProductUpdateScheduler weeklyProductUpdateScheduler;

    @Test
    void sendEmailToAllUsers() {
        UserEmailDTO user1 = new UserEmailDTO()
                .setFirstName("User1")
                .setContactEmail("user1@example.com");
        UserEmailDTO user2 = new UserEmailDTO()
                .setFirstName("User2")
                .setContactEmail("user2@example.com");
        List<UserEmailDTO> users = Arrays.asList(user1, user2);

        when(userService.getAllUserEmails()).thenReturn(users);

        weeklyProductUpdateScheduler.sendProductUpdatesToAllUsers();

        verify(emailService, times(2)).sendEmail(anyString(), eq("This Week's Apple Marketplace Highlights!"), anyString());
    }

    @Test
    void createEmailBody_ShouldCreateCorrectBody() {
        String name = "CurrentUser";

        String emailBody = weeklyProductUpdateScheduler.createEmailBody(name);

        String expectedBody = "Hello CurrentUser,\n\n" +
                "Exciting news from the Apple Marketplace this week!\n\n" +
                "Are you looking to buy or sell? Our platform offers:\n" +
                "🛒 Exclusive Deals: Find great offers on both new and refurbished Apple devices.\n" +
                "🔄 Sell Your Device: Easily list your pre-owned devices and connect with potential buyers.\n\n" +
                "Visit our store to explore these opportunities and make the most of our marketplace!\n\n" +
                "Best regards,\n" +
                "The Apple Marketplace Team";

        assertEquals(expectedBody, emailBody);
    }
}