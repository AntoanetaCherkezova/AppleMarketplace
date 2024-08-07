package com.example.applestore.service.interfaces;
import com.example.applestore.model.dtos.IphoneAddDTO;
import com.example.applestore.model.entity.Device;
import com.example.applestore.model.entity.Iphone;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.IphoneProfileView;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.ModelsWithLargestMemoryView;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface IphoneService {
    boolean availableIPhones();

    void saveIphone(IphoneAddDTO iphoneAddDTO, UserDetails userDetails);

    ModelsWithLargestMemoryView iphoneWithLargestMemory();

    IphoneProfileView createIphoneProfileView(Iphone iphone);

    void saveDevice(Device device);

    Iphone findById(Long deviceId);

    LatestModelDeviceView latestModelIphone();

    List<DeviceView> findLatestIphones();

    List<DeviceView> findLongestWarrantyIphone();

    User findIphoneOwner(Long iPhoneId);

    void deleteIphone(User user, Long deviceId, Iphone iphone);

    void refreshIphone(Long deviceId);

    List<DeviceView> findMyIphones(String username);
}




