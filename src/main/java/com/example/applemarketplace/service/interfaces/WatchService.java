package com.example.applemarketplace.service.interfaces;
import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.model.entity.Device;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.entity.Watch;
import com.example.applemarketplace.model.view.DeviceView;
import com.example.applemarketplace.model.view.LatestModelDeviceView;
import com.example.applemarketplace.model.view.WatchProfileView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface WatchService {

    boolean availableWatches();

    void saveWatch(WatchAddDTO watchAddDTO, UserDetails userDetails);

    LatestModelDeviceView latestModelWatch();

    List<DeviceView> findLatestWatches();

    Watch findById(Long deviceId);

    WatchProfileView createWatchProfileView(Watch watch);

    List<DeviceView> findLongestWarrantyWatch();

    void saveDevice(Device device);

    User findWatchOwner(Long watchId);

    void deleteWatch(User user, Long deviceId, Watch watch);

    void refreshWatch(Long deviceId);

    List<DeviceView> findMyWatches(String username);
}
