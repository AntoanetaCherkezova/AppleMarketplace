package com.example.applestore.web;

import com.example.applestore.model.dtos.IphoneAddDTO;
import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.util.ModelAttributeUtil;
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
@RequestMapping("/iPhones")
public class IphoneController {

    private final IphoneService iphoneService;

    public IphoneController(IphoneService iphoneService) {
        this.iphoneService = iphoneService;
    }

    @ModelAttribute(name = "iPhoneAddDTO")
    public IphoneAddDTO iphoneAddDTO(){
        return new IphoneAddDTO();
    }

    @GetMapping("/add-iPhone")
    public ModelAndView showAddIphoneForm(ModelAndView model){
        ModelAttributeUtil.addEnumsToIphoneModel(model);
        model.setViewName("add-iPhone");
        return model;
    }


    @PostMapping("/add-iPhone")
    public ModelAndView processAddIPhonesForm(ModelAndView model,
                                  @Valid IphoneAddDTO iphoneAddDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal UserDetails userDetails){
        if (bindingResult.hasErrors()){
            ModelAttributeUtil.addEnumsToIphoneModel(model);
            model.setViewName("add-iPhone");
            return model;
        }

        this.iphoneService.saveIphone(iphoneAddDTO,userDetails);
        model.setViewName("redirect:/home");
        return model;
    }
}
