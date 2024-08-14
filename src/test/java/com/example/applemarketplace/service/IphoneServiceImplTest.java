package com.example.applemarketplace.service;
import com.example.applemarketplace.model.dtos.IphoneAddDTO;
import com.example.applemarketplace.model.entity.Device;
import com.example.applemarketplace.model.entity.Iphone;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.enums.*;
import com.example.applemarketplace.model.view.DeviceView;
import com.example.applemarketplace.model.view.IphoneProfileView;
import com.example.applemarketplace.model.view.LatestModelDeviceView;
import com.example.applemarketplace.model.view.ModelsWithLargestMemoryView;
import com.example.applemarketplace.repository.IphoneRepository;
import com.example.applemarketplace.service.interfaces.UserService;
import com.example.applemarketplace.util.ModelAttributeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.applemarketplace.model.enums.InternalMemory.GB_512;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class IphoneServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserService userService;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private IphoneRepository iphoneRepository;
    @InjectMocks
    private IphoneServiceImpl iphoneService;
    @Captor
    private ArgumentCaptor<User> userCaptor;
    private IphoneAddDTO iphoneAddDTO;
    private Iphone iphone;
    private User user;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        user = new User()
                .setUsername("testUsername")
                .setFirstName("Antoniq")
                .setLastName("Bratanova")
                .setPassword("password")
                .setModified(LocalDateTime.now());

        MultipartFile photo1 = mock(MultipartFile.class);
        MultipartFile photo2 = mock(MultipartFile.class);

        iphoneAddDTO = new IphoneAddDTO()
                .setModel("iPhone 15")
                .setDateOfPurchase(LocalDate.now())
                .setPhotosUrls(List.of(photo1, photo2));

        iphone = new Iphone();
        iphone.setStatusDevice("New");
        iphone.setWarranty(24);
        iphone.setModel("iPhone 14");
        iphone.setResolution("1170 x 2532 pixels");
        iphone.setProcessor("A15 Bionic");
        iphone.setCapacityRam(CapacityRam.GB_4);
        iphone.setOperatingSystem("iOS 16");
        iphone.setDisplaySize("6.1 inches");
        iphone.setColour(Colour.BLUE);
        iphone.setDateOfPurchase(LocalDate.now());
        iphone.setPrice(BigDecimal.valueOf(1500.00));
        iphone.setPhotosUrls(List.of("uploaded_photo1_url", "uploaded_photo2_url"));
        iphone.setDisplay(Display.RETINA_DISPLAY);
        iphone.setInternalMemory(InternalMemory.GB_64);
        iphone.setBattery(Battery.LI_ION);
        iphone.setSizes("146.6 x 70.6 x 8.3 ММ");

        userDetails = mock(UserDetails.class);
        lenient().when(userDetails.getUsername()).thenReturn("testUsername");
        lenient().when(cloudinaryService.saveImage(photo1)).thenReturn("uploaded_photo1_url");
        lenient().when(cloudinaryService.saveImage(photo2)).thenReturn("uploaded_photo2_url");
    }

    @Test
    void saveIphone() {
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(iphoneAddDTO, Iphone.class)).thenReturn(iphone);

        iphoneService.saveIphone(iphoneAddDTO, userDetails);
        verify(userService).saveCurrentUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals(user.getUsername(), capturedUser.getUsername());
        assertEquals(1, capturedUser.getMyIphones().size());

        Iphone capturedCar = capturedUser.getMyIphones().get(0);

        assertEquals(iphone.getWarranty(), capturedCar.getWarranty());
        assertEquals(iphone.getModel(), capturedCar.getModel());
        assertEquals(iphone.getPrice(), capturedCar.getPrice());
        assertEquals(iphone.getDateOfPurchase(), capturedCar.getDateOfPurchase());
        assertEquals(iphone.getPhotosUrls(), capturedCar.getPhotosUrls());
        assertEquals(iphone.getDateOfRegister(), capturedCar.getDateOfRegister());
        assertEquals(iphone.getOwner(), capturedCar.getOwner());
    }

    @Test
    void testIphoneWithLargestMemory() {
        Iphone iPhone = new Iphone();
        iPhone.setInternalMemory(GB_512);

        ModelsWithLargestMemoryView expectedView = new ModelsWithLargestMemoryView();
        expectedView.setInternalMemory(GB_512);

        when(iphoneRepository.findIphoneWithLargestMemory()).thenReturn(Collections.singletonList(iPhone));
        when(modelMapper.map(iPhone, ModelsWithLargestMemoryView.class)).thenReturn(expectedView);

        ModelsWithLargestMemoryView result = iphoneService.iphoneWithLargestMemory();

        assertThat(result).isNotNull();
        assertThat(result.getInternalMemory()).isEqualTo(GB_512);
        verify(iphoneRepository).findIphoneWithLargestMemory();
        verify(modelMapper).map(iPhone, ModelsWithLargestMemoryView.class);
    }

    @Test
    void testLatestModelIphone() {

        Iphone iPhone = new Iphone();
        iPhone.setModel("iPhone 14 Pro");

        LatestModelDeviceView expectedView = new LatestModelDeviceView();
        expectedView.setModel("iPhone 14 Pro");

        when(iphoneRepository.findLatestModelIphone()).thenReturn(List.of(iPhone));
        when(modelMapper.map(iPhone, LatestModelDeviceView.class)).thenReturn(expectedView);

        LatestModelDeviceView result = iphoneService.latestModelIphone();

        assertThat(result).isNotNull();
        assertThat(result.getModel()).isEqualTo("iPhone 14 Pro");

        verify(iphoneRepository).findLatestModelIphone();
        verify(modelMapper).map(iPhone, LatestModelDeviceView.class);
    }

    @Test
    void findLongestWarrantyIphone() {
        Iphone longWarrantyIphone = new Iphone();
        longWarrantyIphone.setModel("iPhone 13 Pro");
        longWarrantyIphone.setDateOfPurchase(LocalDate.now().minusYears(1));
        longWarrantyIphone.setPrice(BigDecimal.valueOf(999.99));

        DeviceView expectedView = new DeviceView();
        expectedView.setModel("iPhone 13 Pro");
        expectedView.setDateOfPurchase("2023-08-14");
        expectedView.setPrice("999.99");
        expectedView.setType("iPhone");

        when(iphoneRepository.findLongestWarrantyIphone()).thenReturn(List.of(longWarrantyIphone));
        when(modelMapper.map(longWarrantyIphone, DeviceView.class)).thenReturn(expectedView);

        List<DeviceView> result = iphoneService.findLongestWarrantyIphone();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);

        DeviceView resultView = result.get(0);
        assertThat(resultView.getModel()).isEqualTo("iPhone 13 Pro");
        assertThat(resultView.getDateOfPurchase()).isEqualTo("2023-08-14");
        assertThat(resultView.getPrice()).isEqualTo("999.99");
        assertThat(resultView.getType()).isEqualTo("iPhone");

        verify(iphoneRepository).findLongestWarrantyIphone();
        verify(modelMapper).map(longWarrantyIphone, DeviceView.class);
    }

    @Test
    void findById() {
        when(iphoneRepository.findById(anyLong())).thenReturn(Optional.of(iphone));

        Iphone result = iphoneService.findById(1L);

        assertNotNull(result);
        assertEquals(iphone.getModel(), result.getModel());
    }

    @Test
    void findIphoneOwner() {
        User user = new User();
        user.setUsername("testUsername");

        Iphone iphone = new Iphone();
        iphone.setOwner(user);

        when(iphoneRepository.findById(anyLong())).thenReturn(Optional.of(iphone));
        User result = iphoneService.findIphoneOwner(1L);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }


    @Test
    void availableIphones() {
        when(iphoneRepository.count()).thenReturn(1L);

        boolean result = iphoneService.availableIPhones();

        assertTrue(result);
    }

    @Test
    void saveDevice() {
        Device device = new Iphone();
        when(modelMapper.map(device, Iphone.class)).thenReturn(iphone);

        iphoneService.saveDevice(device);

        verify(iphoneRepository).save(iphone);
    }

    @Test
    void findMyIphones() {
        DeviceView deviceView = new DeviceView()
                .setModel("iPhone 15")
                .setPrice(ModelAttributeUtil.formatPrice(iphone.getPrice()))
                .setDateOfPurchase(ModelAttributeUtil.formatDateWithoutTime(iphone.getDateOfPurchase()))
                .setType("iPhone");
        user.setMyIphones(Collections.singletonList(iphone));
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(iphone, DeviceView.class)).thenReturn(deviceView);

        List<DeviceView> result = iphoneService.findMyIphones("testUsername");

        assertFalse(result.isEmpty());
        assertEquals(deviceView.getModel(), result.get(0).getModel());
        assertEquals(deviceView.getPrice(), result.get(0).getPrice());
    }

    @Test
    void refreshIphone() {
        Iphone iphone = new Iphone();
        iphone.setDateOfRegister(LocalDateTime.of(2018, 1, 1, 0, 0));

        when(iphoneRepository.findById(1L)).thenReturn(Optional.of(iphone));

        iphoneService.refreshIphone(1L);

        verify(iphoneRepository).findById(1L);
        verify(iphoneRepository).save(iphone);
        assertTrue(iphone.getDateOfRegister().isAfter(LocalDateTime.of(2018, 1, 1, 0, 0)));
    }

    @Test
    void createIphoneProfileView() {
        iphone.setDateOfRegister(LocalDateTime.of(2021, 10, 10, 12, 0));

        IphoneProfileView iphoneProfileView = new IphoneProfileView();
        iphoneProfileView.setModel("iPhone 14");
        iphoneProfileView.setDateOfRegister(ModelAttributeUtil.formatDate(iphone.getDateOfRegister()));
        iphoneProfileView.setPrice(ModelAttributeUtil.formatPrice(iphone.getPrice()));

        when(modelMapper.map(iphone, IphoneProfileView.class)).thenReturn(iphoneProfileView);

        IphoneProfileView result = iphoneService.createIphoneProfileView(iphone);

        assertNotNull(result);
        assertEquals(iphone.getModel(), result.getModel());
        assertEquals(ModelAttributeUtil.formatDate(iphone.getDateOfRegister()), result.getDateOfRegister());
        assertEquals(ModelAttributeUtil.formatPrice(iphone.getPrice()), result.getPrice());

        verify(modelMapper).map(iphone, IphoneProfileView.class);
    }

    @Test
    void deleteIphone() {
        user.getMyIphones().add(iphone);
        doNothing().when(iphoneRepository).deleteById(anyLong());

        iphoneService.deleteIphone(user, 1L, iphone);

        assertFalse(user.getMyIphones().contains(iphone));
        verify(iphoneRepository).deleteById(1L);
    }

}