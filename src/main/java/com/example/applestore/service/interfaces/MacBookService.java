package com.example.applestore.service.interfaces;

import com.example.applestore.model.dtos.MacBookAddDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface MacBookService {

    boolean availableMacBooks();

    void saveMacBook(MacBookAddDTO macBookAddDTO, UserDetails userDetails);
}
