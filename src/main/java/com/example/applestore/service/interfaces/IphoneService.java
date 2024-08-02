package com.example.applestore.service.interfaces;

import com.example.applestore.model.dtos.IphoneAddDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface IphoneService {
    boolean availableIPhones();

    void saveIphone(IphoneAddDTO iphoneAddDTO, UserDetails userDetails);
}

