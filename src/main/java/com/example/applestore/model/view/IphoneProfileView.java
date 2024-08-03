package com.example.applestore.model.view;

import com.example.applestore.model.enums.*;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
public class IphoneProfileView {

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

    private Display display;

    private InternalMemory internalMemory;

    private Battery battery;

    private String sizes;

    private boolean dualSim;

    private boolean faceRecognitionSensor;

}
