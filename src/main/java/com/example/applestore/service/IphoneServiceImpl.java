package com.example.applestore.service;
import com.example.applestore.model.dtos.IphoneAddDTO;
import com.example.applestore.model.entity.Iphone;
import com.example.applestore.model.entity.User;
import com.example.applestore.repository.IphoneRepository;
import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IphoneServiceImpl implements IphoneService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final IphoneRepository iphoneRepository;

    public IphoneServiceImpl(ModelMapper modelMapper, UserService userService, IphoneRepository iphoneRepository) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.iphoneRepository = iphoneRepository;
    }

    @Override
    public boolean availableIPhones() {
        return this.iphoneRepository.count() > 0;
    }

    @Override
    public void saveIphone(IphoneAddDTO iphoneAddDTO, UserDetails userDetails) {
        Iphone iphone = modelMapper.map(iphoneAddDTO, Iphone.class);

//        List<String> photoUrls = iphoneAddDTO.getPhotosUrls().stream()
//                .map(cloudinaryService::saveImage)
//                .collect(Collectors.toList());
//        iphone.setPhotosUrls(photoUrls);

        iphone.setReleaseDate(LocalDateTime.now());
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        user.getMyIphones().add(iphone);
        System.out.println();
        this.userService.saveCurrentUser(user);

    }
}
