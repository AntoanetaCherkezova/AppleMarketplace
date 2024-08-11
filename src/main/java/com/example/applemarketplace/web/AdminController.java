package com.example.applemarketplace.web;
import com.example.applemarketplace.model.view.UserControlCenterView;
import com.example.applemarketplace.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/control-center")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView showControlCenter(ModelAndView model) {
        List<UserControlCenterView> users = this.userService.findAllUsersExcludingMyUser();
        model.addObject("users", users);
        model.setViewName("control-center");
        return model;
    }

    @PostMapping("/add-admin/{userId}")
    public ModelAndView addAdminRole(@PathVariable Long userId, ModelAndView model) {
        this.userService.addAdminRole(userId);
        model.setViewName("redirect:/control-center");
        return model;
    }

    @PostMapping("/remove-admin/{userId}")
    public ModelAndView removeAdminRole(@PathVariable Long userId, ModelAndView model) {
        this.userService.removeAdminRole(userId);
        model.setViewName("redirect:/control-center");
        return model;
    }

    @PostMapping("/block-user/{userId}")
    public ModelAndView blockUser(@PathVariable Long userId, ModelAndView model) {
        this.userService.blockUser(userId);
        model.setViewName("redirect:/control-center");
        return model;
    }

    @PostMapping("/unblock-user/{userId}")
    public ModelAndView unblockUser(@PathVariable Long userId, ModelAndView model) {
        this.userService.unblockUser(userId);
        model.setViewName("redirect:/control-center");
        return model;
    }

    @PostMapping("/delete-user/{userId}")
    public ModelAndView deleteUser(@PathVariable Long userId, ModelAndView model) {
        this.userService.deleteUser(userId);
        model.setViewName("redirect:/control-center");
        return model;
    }


}
