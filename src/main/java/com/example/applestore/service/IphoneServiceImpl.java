package com.example.applestore.service;
import com.example.applestore.repository.IphoneRepository;
import com.example.applestore.service.interfaces.IphoneService;
import com.example.applestore.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class IphoneServiceImpl implements IphoneService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final IphoneRepository iphoneRepository;

    public IphoneServiceImpl(ModelMapper modelMapper, UserService userService, IphoneRepository iphoneRepository) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.iphoneRepository = iphoneRepository;
    }

    @Override
    public boolean availableIPhones() {
        return this.iphoneRepository.count() > 0;
    }
}
