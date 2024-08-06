package com.example.applestore.service.interfaces;
import com.example.applestore.model.dtos.WatchAddDTO;
import com.example.applestore.model.entity.Watch;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.WatchProfileView;
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
}
