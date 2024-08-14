package com.example.applemarketplace;
import com.example.applemarketplace.model.dtos.*;
import com.example.applemarketplace.model.entity.Contact;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.entity.UserRole;
import com.example.applemarketplace.model.enums.*;
import com.example.applemarketplace.repository.UserRoleRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataUtils {
    public static IphoneAddDTO createIphoneAddDTO(MockMultipartFile picture, BigDecimal price, Integer warranty) {
        return new IphoneAddDTO()
                .setStatusDevice("New")
                .setWarranty(warranty)
                .setModel("iPhone 14")
                .setResolution("1170 x 2532 pixels")
                .setProcessor("A15 Bionic")
                .setCapacityRam(CapacityRam.GB_4)
                .setOperatingSystem("iOS 16")
                .setDisplaySize("6.1 inches")
                .setColour(Colour.BLUE)
                .setDateOfPurchase(LocalDate.now())
                .setPrice(price)
                .setPhotosUrls(List.of(picture))
                .setDisplay(Display.RETINA_DISPLAY)
                .setInternalMemory(InternalMemory.GB_64)
                .setBattery(Battery.LI_ION)
                .setSizes("146.6 x 70.6 x 8.3 ММ")
                .setDualSim(true)
                .setFaceRecognitionSensor(true);
    }


    public static MacBookAddDTO createMacBookAddDTO(MockMultipartFile picture, BigDecimal price, Integer warranty) {
        return new MacBookAddDTO()
                .setStatusDevice("New")
                .setWarranty(warranty)
                .setModel("MacBook Pro 16")
                .setResolution("3072 x 1920 pixels")
                .setProcessor("Apple M1 Pro")
                .setCapacityRam(CapacityRam.GB_64)
                .setOperatingSystem("macOS Ventura")
                .setDisplaySize("16 inches")
                .setColour(Colour.SILVER)
                .setDateOfPurchase(LocalDate.now())
                .setPrice(price)
                .setPhotosUrls(List.of(picture))
                .setHDD("1TB SSD")
                .setVideoCardType("Integrated");
    }

    public static WatchAddDTO createWatchAddDTO(MockMultipartFile picture, BigDecimal price, Integer warranty) {
        return new WatchAddDTO()
                .setStatusDevice("New")
                .setWarranty(warranty)
                .setModel("Apple Watch Series 8")
                .setResolution("396 x 484 pixels")
                .setProcessor("S8 SiP")
                .setCapacityRam(CapacityRam.GB_1)
                .setOperatingSystem("watchOS 9")
                .setDisplaySize("45 mm")
                .setColour(Colour.SPACE_GRAY)
                .setDateOfPurchase(LocalDate.now())
                .setPrice(price)
                .setPhotosUrls(List.of(picture))
                .setDisplay(Display.LIQUID_RETINA_HD)
                .setInternalMemory(InternalMemory.GB_32)
                .setSizes("45 x 38 x 10.7 mm")
                .setTouchScreenDisplay(true)
                .setSimCardSupport(true);
    }

    public static User createUser(PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        return new User()
                .setUsername("testUsername")
                .setPassword(passwordEncoder.encode("test"))
                .setFirstName("test")
                .setLastName("Username")
                .setAge(33)
                .setBlocked(false)
                .setDateOfRegister(LocalDateTime.now())
                .setModified(LocalDateTime.now())
                .setActive(true)
                .setRoles(new HashSet<>(userRoleRepository.findAll()))
                .setContact(new Contact().setEmail("test@example.com"));
    }

    public static IphoneAddDTO createWrongIphoneAddDTO() {
        return new IphoneAddDTO()
                .setStatusDevice("")
                .setWarranty(-1)
                .setModel("")
                .setResolution("")
                .setProcessor("")
                .setCapacityRam(null)
                .setOperatingSystem("")
                .setDisplaySize("")
                .setColour(null)
                .setDateOfPurchase(LocalDate.now().plusDays(1))
                .setPrice(BigDecimal.valueOf(-1000))
                .setPhotosUrls(List.of())
                .setDisplay(null)
                .setInternalMemory(null)
                .setBattery(null)
                .setSizes("")
                .setDualSim(false)
                .setFaceRecognitionSensor(false);
    }

    public static IphoneAddDTO createIphoneAddDTO(MockMultipartFile picture) {
        return new IphoneAddDTO()
                .setStatusDevice("New")
                .setWarranty(12)
                .setModel("iPhone 14 Pro")
                .setResolution("1170 x 2532 pixels")
                .setProcessor("A15 Bionic")
                .setCapacityRam(CapacityRam.GB_4)
                .setOperatingSystem("iOS 16")
                .setDisplaySize("6.1 inches")
                .setColour(Colour.BLUE)
                .setDateOfPurchase(LocalDate.now())
                .setPrice(BigDecimal.valueOf(999))
                .setPhotosUrls(List.of(picture))
                .setDisplay(Display.RETINA_DISPLAY)
                .setInternalMemory(InternalMemory.GB_64)
                .setBattery(Battery.LI_ION)
                .setSizes("146.6 x 70.6 x 8.3 mm")
                .setDualSim(true)
                .setFaceRecognitionSensor(true);
    }

    public static MacBookAddDTO createWrongMacBookAddDTO() {
        return new MacBookAddDTO()
                .setStatusDevice("")
                .setWarranty(-1)
                .setModel("")
                .setResolution("")
                .setProcessor("")
                .setCapacityRam(null)
                .setOperatingSystem("")
                .setDisplaySize("")
                .setColour(null)
                .setDateOfPurchase(LocalDate.now().plusDays(1))
                .setPrice(BigDecimal.valueOf(-1000))
                .setPhotosUrls(List.of())
                .setHDD("")
                .setVideoCardType("");
    }

    public static MacBookAddDTO createMacBookAddDTO(MockMultipartFile picture) {
        return new MacBookAddDTO()
                .setStatusDevice("New")
                .setWarranty(12)
                .setModel("MacBook Pro 16")
                .setResolution("3072 x 1920 pixels")
                .setProcessor("Apple M1 Max")
                .setCapacityRam(CapacityRam.GB_64)
                .setOperatingSystem("macOS Ventura")
                .setDisplaySize("16 inches")
                .setColour(Colour.SILVER)
                .setDateOfPurchase(LocalDate.now())
                .setPrice(BigDecimal.valueOf(2499))
                .setPhotosUrls(List.of(picture))
                .setHDD("1TB SSD")
                .setVideoCardType("Integrated");
    }

    public static WatchAddDTO createWrongWatchAddDTO() {
        return new WatchAddDTO()
                .setStatusDevice("")
                .setWarranty(-1)
                .setModel("")
                .setResolution("")
                .setProcessor("")
                .setCapacityRam(null)
                .setOperatingSystem("")
                .setDisplaySize("")
                .setColour(null)
                .setDateOfPurchase(LocalDate.now().plusDays(1))
                .setPrice(BigDecimal.valueOf(-1000))
                .setPhotosUrls(List.of())
                .setDisplay(null)
                .setInternalMemory(null)
                .setSizes("")
                .setTouchScreenDisplay(false)
                .setSimCardSupport(false);
    }

    public static WatchAddDTO createWatchAddDTO(MockMultipartFile picture) {
        return new WatchAddDTO()
                .setStatusDevice("New")
                .setWarranty(12)
                .setModel("Apple Watch Series 8")
                .setResolution("396 x 484 pixels")
                .setProcessor("S8 SiP")
                .setCapacityRam(CapacityRam.GB_1)
                .setOperatingSystem("watchOS 9")
                .setDisplaySize("45mm")
                .setColour(Colour.SPACE_GRAY)
                .setDateOfPurchase(LocalDate.now())
                .setPrice(BigDecimal.valueOf(749))
                .setPhotosUrls(List.of(picture))
                .setDisplay(Display.LIQUID_RETINA_HD)
                .setInternalMemory(InternalMemory.GB_32)
                .setSizes("45 x 38 x 10.7 mm")
                .setTouchScreenDisplay(true)
                .setSimCardSupport(true);
    }

    public static UserProfileDTO createUserProfileDTO() {
        return new UserProfileDTO()
                .setFirstName("UpdatedFirstName")
                .setLastName("UpdatedLastName")
                .setContactPhone("1234567890")
                .setCity("UpdatedCity");
    }

    public static UserProfileDTO createWrongUserProfileDTO() {
        return new UserProfileDTO()
                .setFirstName("")
                .setLastName("")
                .setContactPhone("")
                .setCity("");
    }

    public static MockMultipartFile getMockMultipartFile() throws IOException {
        String imagePath = "src/test/resources/static/images/apple-test.jpg";
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        MockMultipartFile picture1 = new MockMultipartFile(
                "photosUrls",
                "device.jpg",
                "image/jpg",
                imageBytes);
        return picture1;
    }

    public static CommentAddDTO createCommentAddDTOForIphone() {
        return new CommentAddDTO()
                .setUser("testUsername")
                .setCommentedAt(LocalDateTime.now())
                .setComment("High price for this iPhone.");
    }

    public static CommentAddDTO createCommentAddDTOForMacBook() {
        return new CommentAddDTO()
                .setUser("testUsername")
                .setCommentedAt(LocalDateTime.now())
                .setComment("High price for this MacBook.");
    }

    public static CommentAddDTO createCommentAddDTOForWatch() {
        return new CommentAddDTO()
                .setUser("testUsername")
                .setCommentedAt(LocalDateTime.now())
                .setComment("High price for this Watch.");
    }

    public static ContactDTO createContactDTO() {
        return new ContactDTO()
                .setFullName("Antoaneta Cherkezova")
                .setEmail("antoaneta.cherkezova@test.com")
                .setSubject("Test Subject")
                .setMessage("Test message");
    }

}
