package com.example.applestore.service;
import com.example.applestore.model.dtos.MacBookAddDTO;
import com.example.applestore.model.entity.Device;
import com.example.applestore.model.entity.MacBook;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.MacBookProfileView;
import com.example.applestore.repository.MacBookRepository;
import com.example.applestore.service.interfaces.MacBookService;
import com.example.applestore.service.interfaces.UserService;
import com.example.applestore.util.ModelAttributeUtil;
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

    @Override
    public LatestModelDeviceView latestModelMacBook() {
        return macBookRepository.findLatestModelMacBook()
                .stream()
                .findFirst()
                .map(macBook -> modelMapper.map(macBook, LatestModelDeviceView.class))
                .orElse(null);
    }

    @Override
    public List<DeviceView> findLatestMacBooks() {
        return macBookRepository.findLatestMacBooks()
                .stream()
                .map(macBook -> {
                    DeviceView view = modelMapper.map(macBook, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(macBook.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(macBook.getPrice()));
                    view.setType("macBook");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public MacBook findById(Long deviceId) {
        return this.macBookRepository.findById(deviceId).orElse(null);
    }

    @Override
    public MacBookProfileView createMacBookProfileView(MacBook macBook) {
        MacBookProfileView macBookProfileView = modelMapper.map(macBook, MacBookProfileView.class);
        macBookProfileView.setReleaseDate(ModelAttributeUtil.formatDate(macBook.getReleaseDate()));
        macBookProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(macBook.getRegisteredOn()));
        macBookProfileView.setPrice(ModelAttributeUtil.formatPrice(macBook.getPrice()));
        return macBookProfileView;
    }

    @Override
    public List<DeviceView> findLongestWarrantyMacBook() {
        return macBookRepository.findLongestWarrantyMacBook()
                .stream()
                .map(macBook -> {
                    DeviceView view = modelMapper.map(macBook, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(macBook.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(macBook.getPrice()));
                    view.setType("macBook");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveDevice(Device device) {
        MacBook macBook = modelMapper.map(device, MacBook.class);
        this.macBookRepository.save(macBook);
    }

    @Override
    public User findMacBookOwner(Long macBookId) {
        MacBook macBook = macBookRepository.findById(macBookId).orElse(null);
        return macBook != null ? macBook.getOwner() : null;
    }

    @Override
    public void deleteMacBook(User user, Long deviceId, MacBook macBook) {
        user.getMyMacBooks().remove(macBook);
        this.macBookRepository.deleteById(deviceId);
    }

    @Override
    public void refreshMacBook(Long deviceId) {
        MacBook macBook = macBookRepository.findById(deviceId).get();
        macBook.setRegisteredOn(LocalDateTime.now());
        macBookRepository.save(macBook);
    }

    @Override
    public List<DeviceView> findMyMacBooks(String username) {
        return this.userService.findByUsername(username).get()
                .getMyMacBooks()
                .stream()
                .map(macBook -> {
                    DeviceView view = modelMapper.map(macBook, DeviceView.class);
                    view.setReleaseDate(ModelAttributeUtil.formatDate(macBook.getReleaseDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(macBook.getPrice()));
                    view.setType("macBook");
                    return view;
                })
                .collect(Collectors.toList());
    }

}
