package com.example.applestore.model.dtos;
import com.example.applestore.model.enums.*;
import com.example.applestore.validation.photosValidator.ValidPhotos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class MacBookAddDTO {

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

    @NotNull(message = "Release Date must be specified.")
    @PastOrPresent(message = "Production date must be in the past or present!")
    private LocalDateTime releaseDate;

    @NotNull(message = "Price must be specified.")
    @Min(value = 1, message = "Price must be greater than zero!")
    private BigDecimal price;

    @ValidPhotos
    private List<MultipartFile> photosUrls;

    @NotBlank(message = "HDD must be specified.")
    private String HDD;

    @NotBlank(message = "Video Card Type are required.")
    private String videoCardType;

}
