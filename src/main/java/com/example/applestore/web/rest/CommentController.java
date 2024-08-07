package com.example.applestore.web.rest;
import com.example.applestore.model.dtos.CommentAddDTO;
import com.example.applestore.model.entity.Comment;
import com.example.applestore.model.entity.Device;
import com.example.applestore.model.view.CommentView;
import com.example.applestore.service.interfaces.CommentService;
import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.service.interfaces.MacBookService;
import com.example.applestore.service.interfaces.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final IphoneService iphoneService;
    private final MacBookService macBookService;
    private final WatchService watchService;

    @Autowired
    public CommentController(CommentService commentService, IphoneService iphoneService, MacBookService macBookService, WatchService watchService) {
        this.commentService = commentService;
        this.iphoneService = iphoneService;
        this.macBookService = macBookService;
        this.watchService = watchService;
    }


    @GetMapping("/{type}/{deviceId}")
    public ResponseEntity<List<CommentView>> getComments(@PathVariable String type, @PathVariable Long deviceId) {
        Device device = type.equals("iPhone") ? iphoneService.findById(deviceId) :
                (type.equals("macBook") ? macBookService.findById(deviceId) :
                (type.equals("watch") ? watchService.findById(deviceId) : null));

        if (device == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CommentView> comments = this.commentService.getCommentsForDevice(device);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/add-comment/{type}/{deviceId}")
    public ResponseEntity<Void> createComment(@PathVariable String type,
                                              @PathVariable Long deviceId,
                                              @AuthenticationPrincipal UserDetails userDetails,
                                              @ModelAttribute CommentAddDTO commentDto) {

        Device device = type.equals("iPhone") ? iphoneService.findById(deviceId) :
                (type.equals("macBook") ? macBookService.findById(deviceId) :
                        (type.equals("watch") ? watchService.findById(deviceId) : null));

        Comment comment = this.commentService.createComment(device, userDetails.getUsername(), commentDto);

        device.getComments().add(comment);

        if (type.equals("iPhone")) {
            iphoneService.saveDevice(device);
        } else if (type.equals("macBook")) {
            macBookService.saveDevice(device);
        } else if (type.equals("watch")) {
            watchService.saveDevice(device);
        }

        String redirectUrl = String.format("/devices/device-profile/%s/%d", type, deviceId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, redirectUrl);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
