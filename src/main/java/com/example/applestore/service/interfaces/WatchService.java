package com.example.applestore.service.interfaces;

import com.example.applestore.model.dtos.WatchAddDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface WatchService {

    boolean availableWatches();

    void saveWatch(WatchAddDTO watchAddDTO, UserDetails userDetails);
}
