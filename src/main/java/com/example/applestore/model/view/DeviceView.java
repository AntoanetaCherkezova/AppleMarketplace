package com.example.applestore.model.view;

import com.example.applestore.model.enums.CapacityRam;
import com.example.applestore.model.enums.Colour;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DeviceView {
    private String id;

    private String statusDevice;

    private String warranty;

    private String model;

    private String resolution;

    private String processor;

    private CapacityRam capacityRam;

    private String operatingSystem;

    private String displaySize;

    private Colour colour;

    private String releaseDate;

    private String price;

    private List<String> photosUrls;

    private LocalDateTime registeredOn;

    private String type;

}
