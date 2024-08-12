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
public class IphoneAddDTO {

    @NotBlank(message = "{iPhone.add.statusDeviceError}")
    private String statusDevice;

    @Min(value = 0, message = "{iPhone.add.warrantyError}")
    private int warranty;

    @NotBlank(message = "{iPhone.add.modelError}")
    private String model;

    @NotBlank(message = "{iPhone.add.resolutionError}")
    private String resolution;

    @NotBlank(message = "{iPhone.add.processorError}")
    private String processor;

    @NotNull(message = "{iPhone.add.capacityRamError}")
    private CapacityRam capacityRam;

    @NotBlank(message = "{iPhone.add.operatingSystemError}")
    private String operatingSystem;

    @NotBlank(message = "{iPhone.add.displaySizeError}")
    private String displaySize;

    @NotNull(message = "{iPhone.add.colourError}")
    private Colour colour;

    @NotNull(message = "{iPhone.add.dateOfPurchaseError}")
    @PastOrPresent(message = "{iPhone.add.dateOfPurchaseErrorPastOrPresent}!")
    private LocalDate dateOfPurchase;

    @NotNull(message = "{iPhone.add.priceErrorNotNull}")
    @Min(value = 1, message = "{iPhone.add.priceError}")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotNull(message = "{iPhone.add.displayError}")
    private Display display;

    @NotNull(message = "{iPhone.add.internalMemoryError}")
    private InternalMemory internalMemory;

    @NotNull(message = "{iPhone.add.batteryError}")
    private Battery battery;

    @NotBlank(message = "{iPhone.add.sizesError}")
    private String sizes;

    private boolean dualSim;

    private boolean faceRecognitionSensor;

}
