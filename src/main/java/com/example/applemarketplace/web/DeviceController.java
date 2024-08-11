package com.example.applemarketplace.web;
import com.example.applemarketplace.model.entity.Iphone;
import com.example.applemarketplace.model.entity.MacBook;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.entity.Watch;
import com.example.applemarketplace.model.view.DeviceView;
import com.example.applemarketplace.service.interfaces.IphoneService;
import com.example.applemarketplace.service.interfaces.MacBookService;
import com.example.applemarketplace.service.interfaces.UserService;
import com.example.applemarketplace.service.interfaces.WatchService;
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
                                       @RequestParam(defaultValue = "5") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        List<DeviceView> devices = Stream.concat(
                        Stream.concat(
                                iphoneService.findLatestIphones().stream(),
                                macBookService.findLatestMacBooks().stream()
                        ),
                        watchService.findLatestWatches().stream()
                ).sorted(Comparator.comparing(DeviceView::getDateOfRegister).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), devices.size());

        List<DeviceView> pagedDevices = devices.subList(start, end);
        Page<DeviceView> allDevices = new PageImpl<>(pagedDevices, pageable, devices.size());

        ModelAndView model = new ModelAndView();
        model.addObject("filter", "all");
        model.addObject("devices", allDevices);
        model.addObject("title", "Device Showcase");
        model.setViewName("devices");
        return model;
    }

    @GetMapping("/iPhones")
    public ModelAndView showIphones(@PageableDefault(sort = "id", size = 5) Pageable pageable, ModelAndView model) {
        List<DeviceView> iPhones = iphoneService.findLatestIphones()
                .stream()
                .sorted(Comparator.comparing(DeviceView::getDateOfRegister).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), iPhones.size());

        List<DeviceView> pagedDevices = iPhones.subList(start, end);
        Page<DeviceView> allDevices = new PageImpl<>(pagedDevices, pageable, iPhones.size());

        model.addObject("filter", "iPhones");
        model.addObject("devices", allDevices);
        model.addObject("title", "Iphone Showcase");
        model.setViewName("devices");
        return model;
    }

    @GetMapping("/macBooks")
    public ModelAndView showMacBooks(@PageableDefault(sort = "id", size = 5) Pageable pageable, ModelAndView model) {
        List<DeviceView> macBooks = macBookService.findLatestMacBooks()
                .stream()
                .sorted(Comparator.comparing(DeviceView::getDateOfRegister).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), macBooks.size());

        List<DeviceView> pagedDevices = macBooks.subList(start, end);
        Page<DeviceView> allDevices = new PageImpl<>(pagedDevices, pageable, macBooks.size());

        model.addObject("filter", "macBooks");
        model.addObject("devices", allDevices);
        model.addObject("title", "MacBooks Showcase");
        model.setViewName("devices");
        return model;
    }

    @GetMapping("/watches")
    public ModelAndView showWatches(@PageableDefault(sort = "id", size = 5) Pageable pageable, ModelAndView model) {
        List<DeviceView> watches = watchService.findLatestWatches()
                .stream()
                .sorted(Comparator.comparing(DeviceView::getDateOfRegister).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), watches.size());

        List<DeviceView> pagedDevices = watches.subList(start, end);
        Page<DeviceView> allDevices = new PageImpl<>(pagedDevices, pageable, watches.size());

        model.addObject("filter", "watches");
        model.addObject("devices", allDevices);
        model.addObject("title", "Watches Showcase");
        model.setViewName("devices");
        return model;
    }

    @GetMapping("/device-profile/{type}/{deviceId}")
    public ModelAndView deviceProfile(ModelAndView model,
                                      @PathVariable String type,
                                      @PathVariable Long deviceId,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) return setNotFound(model);

        return processDeviceProfile(model, type, deviceId, user, userDetails);
    }

    private ModelAndView processDeviceProfile(ModelAndView model, String type, Long deviceId, User user, UserDetails userDetails) {
        boolean isOwner = isOwner(deviceId, user);

        switch (type) {
            case "iPhone":
                Iphone iphone = iphoneService.findById(deviceId);
                if (iphone == null) return setNotFound(model);
                model.addObject("device", iphoneService.createIphoneProfileView(iphone));
                model.addObject("deviceType", "iPhone");
                break;
            case "macBook":
                MacBook macBook = macBookService.findById(deviceId);
                if (macBook == null) return setNotFound(model);
                model.addObject("device", macBookService.createMacBookProfileView(macBook));
                model.addObject("deviceType", "macBook");
                break;
            case "watch":
                Watch watch = watchService.findById(deviceId);
                if (watch == null) return setNotFound(model);
                model.addObject("device", watchService.createWatchProfileView(watch));
                model.addObject("deviceType", "watch");
                break;

            default:
                return setBadRequest(model);
        }
        addObjects(model, userDetails, isOwner);
        model.setViewName("device-profile");
        return model;
    }

    private ModelAndView setNotFound(ModelAndView model) {
        model.setViewName("error");
        model.setStatus(HttpStatus.NOT_FOUND);
        return model;
    }

    private ModelAndView setBadRequest(ModelAndView model) {
        model.setViewName("error");
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }

    private static void addObjects(ModelAndView model, UserDetails userDetails, boolean isOwner) {
        model.addObject("page", "profile");
        model.addObject("isOwner", isOwner);
        model.addObject("user", userDetails.getUsername());
        model.addObject("isAdmin", userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    private boolean isOwner(Long deviceId, User user) {
        return user.getMyIphones().stream().anyMatch(myIphone -> myIphone.equals(iphoneService.findById(deviceId))) ||
                user.getMyMacBooks().stream().anyMatch(myMacBook -> myMacBook.equals(macBookService.findById(deviceId))) ||
                user.getMyWatches().stream().anyMatch(myWatch -> myWatch.equals(watchService.findById(deviceId)));
    }

    @DeleteMapping("/delete-device/{type}/{deviceId}/{page}")
    public ModelAndView deleteDevice(@PathVariable String type,
                                      @PathVariable Long deviceId,
                                      @PathVariable String page,
                                      ModelAndView model) {
        User user = null;
        if (type.equals("iPhone")) {
            Iphone iphone = iphoneService.findById(deviceId);
            if (iphone != null) {
                user = this.iphoneService.findIphoneOwner(deviceId);
                this.iphoneService.deleteIphone(user,deviceId,iphone);
            }
        } else if (type.equals("macBook")) {
            MacBook macBook = macBookService.findById(deviceId);
            if (macBook != null) {
                user = this.macBookService.findMacBookOwner(deviceId);
                this.macBookService.deleteMacBook(user,deviceId,macBook);
            }
        } else if (type.equals("watch")) {
            Watch watch = watchService.findById(deviceId);
            if (watch != null) {
                user = this.watchService.findWatchOwner(deviceId);
                this.watchService.deleteWatch(user,deviceId,watch);
            }
        }
        userService.saveCurrentUser(user);
        model.setViewName(page.equals("profile") ? "redirect:/home" : "redirect:/user/my-devices");
        return model;
    }
}