package com.example.applemarketplace.web;
import com.example.applemarketplace.model.dtos.ContactDTO;
import com.example.applemarketplace.service.interfaces.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact-us")
public class ContactController {

    private final EmailService emailService;

    @Autowired
    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @ModelAttribute(name = "contactDTO")
    public ContactDTO contactDTO() {
        return new ContactDTO();
    }

    @GetMapping
    public ModelAndView showContactForm(ModelAndView model) {
        model.setViewName("contact-us");
        return model;
    }
    @PostMapping("/send-message")
    public ModelAndView sendMessage(@Valid ContactDTO contactDTO, BindingResult result, ModelAndView model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", true);
            model.setViewName("redirect:/contact-us");
            return model;
        }

        String emailContent = "Name: " + contactDTO.getFullName() + "\n" +
                "Email: " + contactDTO.getEmail() + "\n" +
                "Subject: " + contactDTO.getSubject() + "\n" +
                "Message: " + contactDTO.getMessage();

        emailService.sendEmailFromUser(contactDTO.getSubject(), emailContent);
        redirectAttributes.addFlashAttribute("success", true);

        model.setViewName("redirect:/contact-us");
        return model;
    }

}
