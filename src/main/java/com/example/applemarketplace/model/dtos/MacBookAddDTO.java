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
public class MacBookAddDTO {

    @NotBlank(message = "{macBook.add.statusDeviceError}")
    private String statusDevice;

    @Min(value = 0, message = "{macBook.add.warrantyError}!")
    private int warranty;

    @NotBlank(message = "{macBook.add.modelError}")
    private String model;

    @NotBlank(message = "{macBook.add.resolutionError}")
    private String resolution;

    @NotBlank(message = "{macBook.add.processorError}")
    private String processor;

    @NotNull(message = "{macBook.add.capacityRamError}")
    private CapacityRam capacityRam;

    @NotBlank(message = "{macBook.add.operatingSystemError}")
    private String operatingSystem;

    @NotBlank(message = "{macBook.add.displaySizeError}")
    private String displaySize;

    @NotNull(message = "{macBook.add.colourError}")
    private Colour colour;

    @NotNull(message = "{macBook.add.dateOfPurchaseError}")
    @PastOrPresent(message = "{macBook.add.dateOfPurchaseErrorPastOrPresent}")
    private LocalDate dateOfPurchase;

    @NotNull(message = "{macBook.add.priceErrorNotNull}")
    @Min(value = 1, message = "{macBook.add.priceError}")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotBlank(message = "{macBook.add.hddError}")
    private String HDD;

    @NotBlank(message = "{macBook.add.videoCardTypeError}")
    private String videoCardType;

}
