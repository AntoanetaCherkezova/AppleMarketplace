package com.example.applestore.model.view;
import com.example.applestore.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
public class IphoneProfileView {

    private String statusDevice;

    private int warranty;

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

    private String registeredOn;

    private Display display;

    private InternalMemory internalMemory;

    private Battery battery;

    private String sizes;

    private boolean dualSim;

    private boolean faceRecognitionSensor;

}
