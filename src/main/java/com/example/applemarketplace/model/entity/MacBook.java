package com.example.applemarketplace.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "macBooks")
public class MacBook extends Device {

    @Column(nullable = false)
    private String HDD;

    @Column(nullable = false, name = "video_card_type")
    private String videoCardType;

}