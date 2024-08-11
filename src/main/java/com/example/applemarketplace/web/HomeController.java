package com.example.applemarketplace.web;
import com.example.applemarketplace.model.view.DeviceView;
import com.example.applemarketplace.model.view.LatestModelDeviceView;
import com.example.applemarketplace.model.view.ModelsWithLargestMemoryView;
import com.example.applemarketplace.service.interfaces.IphoneService;
import com.example.applemarketplace.service.interfaces.MacBookService;
import com.example.applemarketplace.service.interfaces.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        ModelsWithLargestMemoryView iPhoneWithLargestMemoryView = this.iphoneService.iphoneWithLargestMemory();
        LatestModelDeviceView latestModelIphoneView = this.iphoneService.latestModelIphone();
        LatestModelDeviceView latestModelMacBookView = this.macBookService.latestModelMacBook();
        LatestModelDeviceView latestModelWatchView = this.watchService.latestModelWatch();

        List<DeviceView> deviceWithTheLongestWarranty = Stream.concat(
                        Stream.concat(
                                iphoneService.findLongestWarrantyIphone().stream(),
                                macBookService.findLongestWarrantyMacBook().stream()
                        ),
                        watchService.findLongestWarrantyWatch().stream()
                ).sorted(Comparator.comparing(DeviceView::getWarranty).reversed())
                .limit(5)
                .collect(Collectors.toList());

        model.addObject("availableIPhones", this.iphoneService.availableIPhones());
        model.addObject("availableMacBooks", this.macBookService.availableMacBooks());
        model.addObject("availableWatches", this.watchService.availableWatches());
        model.addObject("iPhoneWithLargestMemoryView", iPhoneWithLargestMemoryView);
        model.addObject("latestModelIphoneView", latestModelIphoneView);
        model.addObject("latestModelMacBookView", latestModelMacBookView);
        model.addObject("latestModelWatchView", latestModelWatchView);
        model.addObject("deviceWithTheLongestWarranty", deviceWithTheLongestWarranty);
        model.setViewName("home");
        return model;
    }
}