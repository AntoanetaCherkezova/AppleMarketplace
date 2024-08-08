package com.example.applestore.model.dtos;

import com.example.applestore.model.enums.*;
import com.example.applestore.validation.photosValidator.ValidPhotos;
import jakarta.validation.constraints.*;
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
public class IphoneAddDTO {

    @NotBlank(message = "Status must be specified.")
    private String statusDevice;

    @Min(value = 0, message = "Warranty must be a non-negative number!")
    private int warranty;

    @NotBlank(message = "Model must be specified.")
    private String model;

    @NotBlank(message = "Resolution must be specified.")
    private String resolution;

    @NotBlank(message = "Processor must be specified.")
    private String processor;

    @NotNull(message = "Capacity RAM must be specified.")
    private CapacityRam capacityRam;

    @NotBlank(message = "Operating System must be specified.")
    private String operatingSystem;

    @NotBlank(message = "Display Size must be specified.")
    private String displaySize;

    @NotNull(message = "Colour must be specified.")
    private Colour colour;

    @NotNull(message = "Date of purchase must be specified.")
    @PastOrPresent(message = "Date of purchase must be in the past or present!")
    private LocalDateTime dateOfPurchase;

    @NotNull(message = "Price must be specified.")
    @Min(value = 1, message = "Price must be greater than zero!")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotNull(message = "Display must be specified.")
    private Display display;

    @NotNull(message = "Internal Memory must be specified.")
    private InternalMemory internalMemory;

    @NotNull(message = "Battery must be specified.")
    private Battery battery;

    @NotBlank(message = "Sizes are required.")
    private String sizes;

    private boolean dualSim;

    private boolean faceRecognitionSensor;

}
