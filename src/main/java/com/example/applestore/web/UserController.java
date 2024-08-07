package com.example.applestore.web;
import com.example.applestore.model.dtos.UserProfileDTO;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.UserProfileView;
import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.service.interfaces.MacBookService;
import com.example.applestore.service.interfaces.UserService;
import com.example.applestore.service.interfaces.WatchService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/user")
public class UserController {
    private final IphoneService iphoneService;
    private final MacBookService macBookService;
    private final WatchService watchService;
    private final UserService userService;

    public UserController(IphoneService iphoneService, MacBookService macBookService, WatchService watchService, UserService userService) {
        this.iphoneService = iphoneService;
        this.macBookService = macBookService;
        this.watchService = watchService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView showUserProfile(@AuthenticationPrincipal UserDetails userDetails,
                                        ModelAndView model) {
        UserProfileView userProfileView = this.userService.mapUserToView(userDetails.getUsername());
        UserProfileDTO userProfileDTO = this.userService.mapUserToDTO(userDetails.getUsername());

        model.addObject("userProfileView", userProfileView);
        model.addObject("userProfile", userProfileDTO);

        model.addObject("roles", userProfileView.getRoles().stream()
                .map(role -> role.getName().getName())
                .collect(Collectors.joining(" & ")));
        model.setViewName("user-profile");
        return model;
    }

    @PostMapping("/profile")
    public ModelAndView updateUserProfile(@Valid @ModelAttribute("userProfile") UserProfileDTO userProfileDTO,
                                          BindingResult bindingResult,
                                          ModelAndView model,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            UserProfileView userProfileView = this.userService.mapUserToView(userDetails.getUsername());
            model.addObject("userProfileView", userProfileView);
            model.addObject(userProfileDTO);
            model.setViewName("user-profile");
            return model;
        }

        userService.updateUser(userProfileDTO,userDetails.getUsername());
        model.setViewName("redirect:/user/profile");
        return model;
    }


    @GetMapping("/my-devices")
    public ModelAndView showAllMyDevices(@PageableDefault(sort = "id",size = 5) Pageable pageable,
                                          ModelAndView model,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        List<DeviceView> devices = Stream.concat(
                        Stream.concat(
                                iphoneService.findMyIphones(userDetails.getUsername()).stream(),
                                macBookService.findMyMacBooks(userDetails.getUsername()).stream()
                        ),
                        watchService.findMyWatches(userDetails.getUsername()).stream()
                ).sorted(Comparator.comparing(DeviceView::getRegisteredOn).reversed())
                .collect(Collectors.toList());


        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), devices.size());

        List<DeviceView> pagedDevices = devices.subList(start, end);
        Page<DeviceView> allMyDevices = new PageImpl<>(pagedDevices, pageable, devices.size());


        model.addObject("page", "myDevice");
        model.addObject("myDevices", allMyDevices);
        model.setViewName("my-devices");
        return model;
    }

    @PostMapping("/refresh-device/{type}/{deviceId}")
    public ModelAndView refreshDevice(ModelAndView model,
                                       @PathVariable String type,
                                       @PathVariable Long deviceId) {
        if (type.equals("iPhone")){
            this.iphoneService.refreshIphone(deviceId);
        }else if (type.equals("macBook")){
            this.macBookService.refreshMacBook(deviceId);
        }else if (type.equals("watch")){
            this.watchService.refreshWatch(deviceId);
        }
        model.setViewName("redirect:/user/my-devices");
        return model;
    }

}
