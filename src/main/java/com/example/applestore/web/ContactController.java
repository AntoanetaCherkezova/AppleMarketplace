package com.example.applestore.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contact-us")
public class ContactController {

    @GetMapping
    public ModelAndView showContactForm(ModelAndView model) {
        model.setViewName("contact-us");
        return model;
    }

}
