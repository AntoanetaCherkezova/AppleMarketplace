package com.example.applemarketplace.model.dtos;

import com.example.applemarketplace.model.enums.*;
import com.example.applemarketplace.validation.photosValidator.ValidPhotos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class WatchAddDTO {
    @NotBlank(message = "{watch.add.statusDeviceError}")
    private String statusDevice;

    @Min(value = 0, message = "{watch.add.warrantyError}")
    private int warranty;

    @NotBlank(message = "{watch.add.modelError}")
    private String model;

    @NotBlank(message = "{watch.add.resolutionError}")
    private String resolution;

    @NotBlank(message = "{watch.add.processorError}")
    private String processor;

    @NotNull(message = "{watch.add.capacityRamError}")
    private CapacityRam capacityRam;

    @NotBlank(message = "{watch.add.operatingSystemError}")
    private String operatingSystem;

    @NotBlank(message = "{watch.add.displaySizeError}")
    private String displaySize;

    @NotNull(message = "{watch.add.colourError}")
    private Colour colour;

    @NotNull(message = "{watch.add.dateOfPurchaseError}")
    @PastOrPresent(message = "{watch.add.dateOfPurchaseErrorPastOrPresent}")
    private LocalDate dateOfPurchase;

    @NotNull(message = "{watch.add.priceErrorNotNull}")
    @Min(value = 1, message = "{watch.add.priceError}")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotNull(message = "{watch.add.displayError}")
    private Display display;

    @NotNull(message = "{watch.add.internalMemoryError}")
    private InternalMemory internalMemory;

    @NotBlank(message = "{watch.add.sizesError}")
    private String sizes;

    private boolean touchScreenDisplay;

    private boolean simCardSupport;

}
