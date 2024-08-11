package com.example.applemarketplace.model.entity;

import com.example.applemarketplace.model.enums.Display;
import com.example.applemarketplace.model.enums.InternalMemory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "watches")
public class Watch extends Device {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Display display;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InternalMemory internalMemory;

    @Column(nullable = false)
    private String sizes;

    @Column(nullable = false, name = "touch_screen_display")
    private boolean touchScreenDisplay;

    @Column(nullable = false, name = "sim_card_support")
    private boolean simCardSupport;

}