package com.example.applestore.service;
import com.example.applestore.model.dtos.WatchAddDTO;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.entity.Watch;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.WatchProfileView;
import com.example.applestore.repository.WatchRepository;
import com.example.applestore.service.interfaces.UserService;
import com.example.applestore.service.interfaces.WatchService;
import com.example.applestore.util.ModelAttributeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchServiceImpl implements WatchService {
    private final WatchRepository watchRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;

    public WatchServiceImpl(WatchRepository watchRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, UserService userService) {
        this.watchRepository = watchRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    @Override
    public boolean availableWatches() {
        return this.watchRepository.count() > 0;
    }

    @Override
    public void saveWatch(WatchAddDTO watchAddDTO, UserDetails userDetails) {
        Watch watch = modelMapper.map(watchAddDTO, Watch.class);

        List<String> photoUrls = watchAddDTO.getPhotosUrls().stream()
                .map(cloudinaryService::saveImage)
                .collect(Collectors.toList());
        watch.setPhotosUrls(photoUrls);

        watch.setRegisteredOn(LocalDateTime.now());
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        watch.setOwner(user);
        user.getMyWatches().add(watch);
        this.userService.saveCurrentUser(user);
    }

    @Override
    public LatestModelDeviceView latestModelWatch() {
        return watchRepository.findLatestModelWatch()
                .stream()
                .findFirst()
                .map(watch -> modelMapper.map(watch, LatestModelDeviceView.class))
                .orElse(null);
    }

    @Override
    public List<DeviceView> findLatestWatches() {
        return watchRepository.findLatestWatches()
                .stream()
                .map(watch -> {
                    DeviceView view = modelMapper.map(watch, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(watch.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(watch.getPrice()));
                    view.setType("watch");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Watch findById(Long deviceId) {
        return this.watchRepository.findById(deviceId).orElse(null);
    }

    @Override
    public WatchProfileView createWatchProfileView(Watch watch) {
        WatchProfileView watchProfileView = modelMapper.map(watch, WatchProfileView.class);
        watchProfileView.setReleaseDate(ModelAttributeUtil.formatDate(watch.getReleaseDate()));
        watchProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(watch.getRegisteredOn()));
        watchProfileView.setPrice(ModelAttributeUtil.formatPrice(watch.getPrice()));
        return watchProfileView;
    }

    @Override
    public List<DeviceView> findLongestWarrantyWatch() {
        return watchRepository.findLongestWarrantyWatch()
                .stream()
                .map(watch -> {
                    DeviceView view = modelMapper.map(watch, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(watch.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(watch.getPrice()));
                    view.setType("watch");
                    return view;
                })
                .collect(Collectors.toList());
    }
}
