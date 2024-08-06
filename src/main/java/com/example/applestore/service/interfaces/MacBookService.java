package com.example.applestore.service.interfaces;
import com.example.applestore.model.dtos.MacBookAddDTO;
import com.example.applestore.model.entity.MacBook;
import com.example.applestore.model.view.DeviceView;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.MacBookProfileView;
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
}
