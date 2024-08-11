package com.example.applemarketplace.service.interfaces;
import com.example.applemarketplace.model.dtos.MacBookAddDTO;
import com.example.applemarketplace.model.entity.Device;
import com.example.applemarketplace.model.entity.MacBook;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.view.DeviceView;
import com.example.applemarketplace.model.view.LatestModelDeviceView;
import com.example.applemarketplace.model.view.MacBookProfileView;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface MacBookService {

    boolean availableMacBooks();

    void saveMacBook(MacBookAddDTO macBookAddDTO, UserDetails userDetails);

    LatestModelDeviceView latestModelMacBook();

    List<DeviceView> findLatestMacBooks();

    MacBook findById(Long deviceId);

    MacBookProfileView createMacBookProfileView(MacBook macBook);

    List<DeviceView> findLongestWarrantyMacBook();

    void saveDevice(Device device);

    User findMacBookOwner(Long macBookId);

    void deleteMacBook(User user, Long deviceId, MacBook macBook);

    void refreshMacBook(Long deviceId);

    List<DeviceView> findMyMacBooks(String username);
}
