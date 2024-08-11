package com.example.applemarketplace.web;

import com.example.applemarketplace.model.dtos.UserRegisterDTO;
import com.example.applemarketplace.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "userRegisterDTO")
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(ModelAndView model) {
        model.setViewName("register");
        return model;
    }

    @PostMapping("/register")
    public ModelAndView processRegistrationForm(ModelAndView model,
                                     @Valid UserRegisterDTO userRegisterDTO,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.setViewName("register");
            return model;
        }

        this.userService.saveUser(userRegisterDTO);

        model.setViewName("redirect:/");
        return model;
    }
}
