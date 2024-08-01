package com.example.applestore.web;

import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.service.interfaces.MacBookService;
import com.example.applestore.service.interfaces.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final IphoneService iphoneService;
    private final MacBookService macBookService;
    private final WatchService watchService;

    @Autowired
    public HomeController(IphoneService iphoneService, MacBookService macBookService, WatchService watchService) {
        this.iphoneService = iphoneService;
        this.macBookService = macBookService;
        this.watchService = watchService;
    }

    @GetMapping()
    public ModelAndView home(ModelAndView model){
        model.addObject("availableIPhones", this.iphoneService.availableIPhones());
        model.addObject("availableMacBooks", this.macBookService.availableMacBooks());
        model.addObject("availableWatches", this.watchService.availableWatches());
        model.setViewName("home");
        return model;
    }
}