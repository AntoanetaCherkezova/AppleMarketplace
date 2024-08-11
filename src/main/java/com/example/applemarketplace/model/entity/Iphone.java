package com.example.applemarketplace.model.entity;

import com.example.applemarketplace.model.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "iPhones")
public class Iphone extends Device {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Display display;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InternalMemory internalMemory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Battery battery;

    @Column(nullable = false)
    private boolean dualSim;

    @Column(nullable = false, name = "face_recognition_sensor")
    private boolean faceRecognitionSensor;

    @Column(nullable = false)
    private String sizes;


}