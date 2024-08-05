package com.example.applestore.model.view;

import com.example.applestore.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class MacBookProfileView {
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

    private List<MultipartFile> photosUrls;

    private String registeredOn;

    private Battery HDD;

    private String videoCardType;
}
