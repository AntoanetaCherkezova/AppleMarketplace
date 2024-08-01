package com.example.applestore.init;

import jakarta.annotation.PostConstruct;

public interface DataBaseInitService {
    @PostConstruct
    void init();
}
