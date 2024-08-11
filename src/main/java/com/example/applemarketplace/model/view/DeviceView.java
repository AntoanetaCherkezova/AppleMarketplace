package com.example.applemarketplace.model.view;

import com.example.applemarketplace.model.enums.CapacityRam;
import com.example.applemarketplace.model.enums.Colour;
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

    private int warranty;

    private String model;

    private String resolution;

    private String processor;

    private CapacityRam capacityRam;

    private String operatingSystem;

    private String displaySize;

    private Colour colour;

    private String dateOfPurchase;

    private String price;

    private List<String> photosUrls;

    private LocalDateTime dateOfRegister;

    private String type;

}
