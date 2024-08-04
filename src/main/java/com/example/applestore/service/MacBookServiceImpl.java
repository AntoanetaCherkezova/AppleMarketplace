package com.example.applestore.service;
import com.example.applestore.model.dtos.MacBookAddDTO;
import com.example.applestore.model.entity.MacBook;
import com.example.applestore.model.entity.User;
import com.example.applestore.repository.MacBookRepository;
import com.example.applestore.service.interfaces.MacBookService;
import com.example.applestore.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MacBookServiceImpl implements MacBookService {

    private final MacBookRepository macBookRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;

    public MacBookServiceImpl(MacBookRepository macBookRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, UserService userService) {
        this.macBookRepository = macBookRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    @Override
    public boolean availableMacBooks() {
        return this.macBookRepository.count() > 0;
    }

    @Override
    public void saveMacBook(MacBookAddDTO macBookAddDTO, UserDetails userDetails) {
        MacBook macBook = modelMapper.map(macBookAddDTO, MacBook.class);

        List<String> photoUrls = macBookAddDTO.getPhotosUrls().stream()
                .map(cloudinaryService::saveImage)
                .collect(Collectors.toList());
        macBook.setPhotosUrls(photoUrls);

        macBook.setRegisteredOn(LocalDateTime.now());
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        macBook.setOwner(user);
        user.getMyMacBooks().add(macBook);
        this.userService.saveCurrentUser(user);
    }
}
