package com.example.applemarketplace.init;

import jakarta.annotation.PostConstruct;

public interface DataBaseInitService {
    @PostConstruct
    void init();
}
