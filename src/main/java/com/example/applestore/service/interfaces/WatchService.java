package com.example.applestore.service.interfaces;
import com.example.applestore.model.dtos.WatchAddDTO;
import com.example.applestore.model.entity.Device;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.entity.Watch;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.WatchProfileView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
