package com.example.applemarketplace.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AboutController {

    @GetMapping("/about-us")
    public ModelAndView about(ModelAndView model) {
        model.setViewName("about-us");
        return model;
    }
}
