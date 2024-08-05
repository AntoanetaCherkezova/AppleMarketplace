package com.example.applestore.service.interfaces;

import com.example.applestore.model.dtos.MacBookAddDTO;
import com.example.applestore.model.view.LatestModelDeviceView;
import com.example.applestore.model.view.ModelsWithLargestMemoryView;
import org.springframework.security.core.userdetails.UserDetails;

public interface MacBookService {

    boolean availableMacBooks();

    void saveMacBook(MacBookAddDTO macBookAddDTO, UserDetails userDetails);


    LatestModelDeviceView latestModelMacBook();

}
