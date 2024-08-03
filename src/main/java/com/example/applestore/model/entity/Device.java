package com.example.applestore.model.entity;

import com.example.applestore.model.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public abstract class Device extends BaseEntity {

    @Column(nullable = false)
    private String statusDevice;

    @Column(nullable = false)
    private String warranty;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String resolution;

    @Column(nullable = false)
    private String processor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CapacityRam capacityRam;

    @Column(nullable = false, name = "operating_system")
    private String operatingSystem;

    @Column(nullable = false, name = "display_size")
    private String displaySize;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Colour colour;

    @Column(nullable = false, name = "release_date")
    private LocalDateTime releaseDate;

    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection
    private List<String> photosUrls;

    @Column(nullable = false, name = "registered_on")
    private LocalDateTime registeredOn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Device() {
        this.comments = new ArrayList<>();
        this.photosUrls = new ArrayList<>();
    }
}