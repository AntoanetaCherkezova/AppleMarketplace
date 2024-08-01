package com.example.applestore.service;
import com.example.applestore.repository.IphoneRepository;
import com.example.applestore.service.interfaces.MacBookService;
import org.springframework.stereotype.Service;

@Service
public class MacBookServiceImpl implements MacBookService {
    private final IphoneRepository iphoneRepository;

    public MacBookServiceImpl(IphoneRepository iphoneRepository) {
        this.iphoneRepository = iphoneRepository;
    }

    @Override
    public boolean availableMacBooks() {
        return this.iphoneRepository.count() > 0;
    }
}
