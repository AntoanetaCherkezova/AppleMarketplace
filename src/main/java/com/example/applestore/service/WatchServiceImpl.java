package com.example.applestore.service;
import com.example.applestore.repository.IphoneRepository;
import com.example.applestore.repository.WatchRepository;
import com.example.applestore.service.interfaces.WatchService;
import org.springframework.stereotype.Service;

@Service
public class WatchServiceImpl implements WatchService {
    private final WatchRepository watchRepository;

    public WatchServiceImpl(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    @Override
    public boolean availableWatches() {
        return this.watchRepository.count() > 0;
    }
}
