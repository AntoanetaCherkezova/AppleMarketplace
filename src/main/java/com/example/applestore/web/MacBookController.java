package com.example.applestore.web;
import com.example.applestore.model.dtos.MacBookAddDTO;
import com.example.applestore.service.interfaces.MacBookService;
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
@RequestMapping("/macBooks")
public class MacBookController {

    private final MacBookService macBookService;

    public MacBookController(MacBookService macBookService) {
        this.macBookService = macBookService;
    }

    @ModelAttribute(name = "macBookAddDTO")
    public MacBookAddDTO macBookAddDTO(){
        return new MacBookAddDTO();
    }

    @GetMapping("/add-macBook")
    public ModelAndView showAddMacBookForm(ModelAndView model){
        ModelAttributeUtil.addEnumsToMacBookModel(model);
        model.setViewName("add-macBook");
        return model;
    }


    @PostMapping("/add-macBook")
    public ModelAndView processAddMacBookForm(ModelAndView model,
                                              @Valid MacBookAddDTO macBookAddDTO,
                                              BindingResult bindingResult,
                                              @AuthenticationPrincipal UserDetails userDetails){
        if (bindingResult.hasErrors()){
            ModelAttributeUtil.addEnumsToMacBookModel(model);
            model.setViewName("add-macBook");
            return model;
        }

        this.macBookService.saveMacBook(macBookAddDTO,userDetails);
        model.setViewName("redirect:/home");
        return model;
    }
}
