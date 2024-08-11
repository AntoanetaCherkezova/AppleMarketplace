package com.example.applemarketplace.model.view;
import com.example.applemarketplace.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class WatchProfileView {
    private Long id;

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

    private String dateOfRegister;

    private Display display;

    private InternalMemory internalMemory;

    private String sizes;

    private boolean touchScreenDisplay;

    private boolean simCardSupport;
}
