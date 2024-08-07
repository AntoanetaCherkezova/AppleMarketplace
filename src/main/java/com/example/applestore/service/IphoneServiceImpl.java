package com.example.applestore.service;
import com.example.applestore.model.dtos.IphoneAddDTO;
import com.example.applestore.model.entity.Device;
import com.example.applestore.model.entity.Iphone;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.IphoneProfileView;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.ModelsWithLargestMemoryView;
import com.example.applestore.repository.IphoneRepository;
import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.service.interfaces.UserService;
import com.example.applestore.util.ModelAttributeUtil;
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
    private final CloudinaryService cloudinaryService;

    public IphoneServiceImpl(ModelMapper modelMapper, UserService userService, IphoneRepository iphoneRepository, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.iphoneRepository = iphoneRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public boolean availableIPhones() {
        return this.iphoneRepository.count() > 0;
    }

    @Override
    public void saveIphone(IphoneAddDTO iphoneAddDTO, UserDetails userDetails) {
        Iphone iphone = modelMapper.map(iphoneAddDTO, Iphone.class);

        List<String> photoUrls = iphoneAddDTO.getPhotosUrls().stream()
                .map(cloudinaryService::saveImage)
                .collect(Collectors.toList());
        iphone.setPhotosUrls(photoUrls);

        iphone.setRegisteredOn(LocalDateTime.now());
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        iphone.setOwner(user);
        user.getMyIphones().add(iphone);
        this.userService.saveCurrentUser(user);
    }

    @Override
    public ModelsWithLargestMemoryView iphoneWithLargestMemory() {
        return iphoneRepository.findIphoneWithLargestMemory()
                .stream()
                .findFirst()
                .map(iPhone -> modelMapper.map(iPhone, ModelsWithLargestMemoryView.class))
                .orElse(null);
    }
    @Override
    public LatestModelDeviceView latestModelIphone() {
        return iphoneRepository.findLatestModelIphone()
                .stream()
                .findFirst()
                .map(iPhone -> modelMapper.map(iPhone, LatestModelDeviceView.class))
                .orElse(null);
    }

    @Override
    public List<DeviceView> findLatestIphones() {
        return iphoneRepository.findLatestIphones()
                .stream()
                .map(iPhone -> {
                    DeviceView view = modelMapper.map(iPhone, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(iPhone.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(iPhone.getPrice()));
                    view.setType("iPhone");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceView> findLongestWarrantyIphone() {
        return iphoneRepository.findLongestWarrantyIphone()
                .stream()
                .map(iPhone -> {
                    DeviceView view = modelMapper.map(iPhone, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(iPhone.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(iPhone.getPrice()));
                    view.setType("iPhone");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public User findIphoneOwner(Long iPhoneId) {
        Iphone iphone = iphoneRepository.findById(iPhoneId).orElse(null);
        return iphone != null ? iphone.getOwner() : null;
    }

    @Override
    public void deleteIphone(User user, Long deviceId, Iphone iphone) {
        user.getMyIphones().remove(iphone);
        this.iphoneRepository.deleteById(deviceId);
    }

    @Override
    public void refreshIphone(Long deviceId) {
        Iphone iphone = iphoneRepository.findById(deviceId).get();
        iphone.setRegisteredOn(LocalDateTime.now());
        iphoneRepository.save(iphone);
    }

    @Override
    public List<DeviceView> findMyIphones(String username) {
        return this.userService.findByUsername(username).get()
                .getMyIphones()
                .stream()
                .map(iphone -> {
                    DeviceView view = modelMapper.map(iphone, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(iphone.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(iphone.getPrice()));
                    view.setType("iPhone");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public IphoneProfileView createIphoneProfileView(Iphone iphone) {
        IphoneProfileView iphoneProfileView = modelMapper.map(iphone, IphoneProfileView.class);
        iphoneProfileView.setReleaseDate(ModelAttributeUtil.formatDate(iphone.getReleaseDate()));
        iphoneProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(iphone.getRegisteredOn()));
        iphoneProfileView.setPrice(ModelAttributeUtil.formatPrice(iphone.getPrice()));
        return iphoneProfileView;
    }

    @Override
    public void saveDevice(Device device) {
        Iphone iphone = modelMapper.map(device, Iphone.class);
        this.iphoneRepository.save(iphone);
    }

    @Override
    public Iphone findById(Long deviceId) {
        return this.iphoneRepository.findById(deviceId).orElse(null);
    }


}
