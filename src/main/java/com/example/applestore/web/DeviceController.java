package com.example.applestore.web;
import com.example.applestore.model.entity.Iphone;
import com.example.applestore.model.entity.MacBook;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.entity.Watch;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.service.interfaces.MacBookService;
import com.example.applestore.service.interfaces.UserService;
import com.example.applestore.service.interfaces.WatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    private final IphoneService iphoneService;
    private final MacBookService macBookService;
    private final WatchService watchService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public DeviceController(IphoneService iphoneService, MacBookService macBookService, WatchService watchService, UserService userService, ModelMapper modelMapper) {
        this.iphoneService = iphoneService;
        this.macBookService = macBookService;
        this.watchService = watchService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ModelAndView showAllDevices(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        List<DeviceView> devices = Stream.concat(
                        Stream.concat(
                                iphoneService.findLatestIphones().stream(),
                                macBookService.findLatestMacBoks().stream()
                        ),
                        watchService.findLatestWatches().stream()
                ).sorted(Comparator.comparing(DeviceView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), devices.size());

        Page<DeviceView> allDevices = new PageImpl<>(
                devices.subList(start, end),
                pageable,
                devices.size()
        );

        ModelAndView model = new ModelAndView();
        model.addObject("filter", "all");
        model.addObject("devices", allDevices);
        model.addObject("title", "Device Showcase");
        model.setViewName("devices");
        return model;
    }

    @GetMapping("/iPhones")
    public ModelAndView showIphones(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model, Locale locale) {
        List<DeviceView> iPhones = iphoneService.findLatestIphones()
                .stream()
                .sorted(Comparator.comparing(DeviceView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), iPhones.size());

        Page<DeviceView> allDevices = new PageImpl<>(
                iPhones.subList(start, end),
                pageable,
                iPhones.size()
        );

        model.addObject("filter", "iPhones");
        model.addObject("devices", allDevices);
        model.addObject("title", "Iphone Showcase");
        model.setViewName("devices");
        return model;
    }

    @GetMapping("/macBooks")
    public ModelAndView showMacBooks(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model, Locale locale) {
        List<DeviceView> macBooks = macBookService.findLatestMacBoks()
                .stream()
                .sorted(Comparator.comparing(DeviceView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), macBooks.size());

        Page<DeviceView> allDevices = new PageImpl<>(
                macBooks.subList(start, end),
                pageable,
                macBooks.size()
        );

        model.addObject("filter", "macBooks");
        model.addObject("devices", allDevices);
        model.addObject("title", "MacBooks Showcase");
        model.setViewName("devices");
        return model;
    }

    @GetMapping("/watches")
    public ModelAndView showWatches(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model, Locale locale) {
        List<DeviceView> watches = watchService.findLatestWatches()
                .stream()
                .sorted(Comparator.comparing(DeviceView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), watches.size());

        Page<DeviceView> allDevices = new PageImpl<>(
                watches.subList(start, end),
                pageable,
                watches.size()
        );

        model.addObject("filter", "watches");
        model.addObject("devices", allDevices);
        model.addObject("title", "Watches Showcase");
        model.setViewName("devices");
        return model;
    }


}