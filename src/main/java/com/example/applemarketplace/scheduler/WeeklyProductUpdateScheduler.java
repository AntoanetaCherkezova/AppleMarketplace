package com.example.applemarketplace.scheduler;
import com.example.applemarketplace.model.dtos.UserEmailDTO;
import com.example.applemarketplace.service.interfaces.EmailService;
import com.example.applemarketplace.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class WeeklyProductUpdateScheduler {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public WeeklyProductUpdateScheduler(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 18 * * FRI")
    public void sendProductUpdatesToAllUsers() {
        List<UserEmailDTO> users = this.userService.getAllUserEmails();
        String emailSubject = "This Week's Apple Marketplace Highlights!";

        users.forEach(userProfile -> {
            this.emailService.sendEmail(userProfile.getContactEmail(), emailSubject,
                    createEmailBody(userProfile.getFirstName()));
        });
    }

    public String createEmailBody(String name) {
        StringBuilder sb = new StringBuilder();

        sb.append("Hello ").append(name).append(",\n\n")
                .append("Exciting news from the Apple Marketplace this week!\n\n")
                .append("Are you looking to buy or sell? Our platform offers:\n")
                .append("🛒 Exclusive Deals: Find great offers on both new and refurbished Apple devices.\n")
                .append("🔄 Sell Your Device: Easily list your pre-owned devices and connect with potential buyers.\n\n")
                .append("Visit our store to explore these opportunities and make the most of our marketplace!\n\n")
                .append("Best regards,\n")
                .append("The Apple Marketplace Team");

        return sb.toString();
    }
}
