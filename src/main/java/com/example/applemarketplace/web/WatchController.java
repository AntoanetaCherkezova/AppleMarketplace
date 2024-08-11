package com.example.applemarketplace.web;

import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.service.interfaces.WatchService;
import com.example.applemarketplace.util.ModelAttributeUtil;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/watches")
public class WatchController {
    private final WatchService watchService;

    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    @ModelAttribute(name = "watchAddDTO")
    public WatchAddDTO watchAddDTO(){
        return new WatchAddDTO();
    }

    @GetMapping("/add-watch")
    public ModelAndView showAddWatchForm(ModelAndView model){
        ModelAttributeUtil.addEnumsToWatchModel(model);
        model.setViewName("add-watch");
        return model;
    }


    @PostMapping("/add-watch")
    public ModelAndView processAddMacBookForm(ModelAndView model,
                                              @Valid WatchAddDTO watchAddDTO,
                                              BindingResult bindingResult,
                                              @AuthenticationPrincipal UserDetails userDetails){
        if (bindingResult.hasErrors()){
            ModelAttributeUtil.addEnumsToWatchModel(model);
            model.setViewName("add-watch");
            return model;
        }

        this.watchService.saveWatch(watchAddDTO,userDetails);
        model.setViewName("redirect:/home");
        return model;
    }
}
