package com.example.applemarketplace.service;

import com.example.applemarketplace.model.dtos.MacBookAddDTO;
import com.example.applemarketplace.model.entity.Device;
import com.example.applemarketplace.model.entity.Iphone;
import com.example.applemarketplace.model.entity.MacBook;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.enums.*;
import com.example.applemarketplace.model.view.*;
import com.example.applemarketplace.repository.MacBookRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MacBookServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserService userService;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private MacBookRepository macBookRepository;
    @InjectMocks
    private MacBookServiceImpl macBookService;
    @Captor
    private ArgumentCaptor<User> userCaptor;
    private MacBookAddDTO macBookAddDTO;
    private MacBook macBook;
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

        macBookAddDTO = new MacBookAddDTO()
                .setModel("MacBook Pro 15")
                .setDateOfPurchase(LocalDate.now())
                .setPhotosUrls(List.of(photo1, photo2));

        macBook = new MacBook();
        macBook.setStatusDevice("New");
        macBook.setWarranty(24);
        macBook.setModel("MacBook Pro 15");
        macBook.setResolution("3072 x 1920 pixels");
        macBook.setProcessor("Apple M1 Pro");
        macBook.setCapacityRam(CapacityRam.GB_64);
        macBook.setOperatingSystem("macOS Ventura");
        macBook.setDisplaySize("16 inches");
        macBook.setColour(Colour.SILVER);
        macBook.setDateOfPurchase(LocalDate.now());
        macBook.setPrice(BigDecimal.valueOf(2500.00));
        macBook.setPhotosUrls(List.of("uploaded_photo1_url", "uploaded_photo2_url"));
        macBook.setHDD("1TB SSD");
        macBook.setVideoCardType("Integrated");

        userDetails = mock(UserDetails.class);
        lenient().when(userDetails.getUsername()).thenReturn("testUsername");
        lenient().when(cloudinaryService.saveImage(photo1)).thenReturn("uploaded_photo1_url");
        lenient().when(cloudinaryService.saveImage(photo2)).thenReturn("uploaded_photo2_url");
    }

    @Test
    void saveMacBook() {
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(macBookAddDTO, MacBook.class)).thenReturn(macBook);

        macBookService.saveMacBook(macBookAddDTO, userDetails);
        verify(userService).saveCurrentUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals(user.getUsername(), capturedUser.getUsername());
        assertEquals(1, capturedUser.getMyMacBooks().size());

        MacBook capturedCar = capturedUser.getMyMacBooks().get(0);

        assertEquals(macBook.getWarranty(), capturedCar.getWarranty());
        assertEquals(macBook.getModel(), capturedCar.getModel());
        assertEquals(macBook.getPrice(), capturedCar.getPrice());
        assertEquals(macBook.getDateOfPurchase(), capturedCar.getDateOfPurchase());
        assertEquals(macBook.getPhotosUrls(), capturedCar.getPhotosUrls());
        assertEquals(macBook.getDateOfRegister(), capturedCar.getDateOfRegister());
        assertEquals(macBook.getOwner(), capturedCar.getOwner());
    }

    @Test
    void testLatestModelMacBook() {
        MacBook macBook = new MacBook();
        macBook.setModel("MacBook Pro 15");

        LatestModelDeviceView expectedView = new LatestModelDeviceView();
        expectedView.setModel("MacBook Pro 15");

        when(macBookRepository.findLatestModelMacBook()).thenReturn(List.of(macBook));
        when(modelMapper.map(macBook, LatestModelDeviceView.class)).thenReturn(expectedView);

        LatestModelDeviceView result = macBookService.latestModelMacBook();

        assertThat(result).isNotNull();
        assertThat(result.getModel()).isEqualTo("MacBook Pro 15");

        verify(macBookRepository).findLatestModelMacBook();
        verify(modelMapper).map(macBook, LatestModelDeviceView.class);
    }

    @Test
    void findLatestModelMacBook() {
        MacBook latestMacBook = new MacBook();
        latestMacBook.setModel("MacBook Pro 16");
        latestMacBook.setDateOfPurchase(LocalDate.now().minusMonths(6));
        latestMacBook.setPrice(BigDecimal.valueOf(2399.99));

        LatestModelDeviceView expectedView = new LatestModelDeviceView();
        expectedView.setModel("MacBook Pro 16");

        when(macBookRepository.findLatestModelMacBook()).thenReturn(List.of(latestMacBook));
        when(modelMapper.map(latestMacBook, LatestModelDeviceView.class)).thenReturn(expectedView);

        LatestModelDeviceView result = macBookService.latestModelMacBook();

        assertThat(result).isNotNull();
        assertThat(result.getModel()).isEqualTo("MacBook Pro 16");

        verify(macBookRepository).findLatestModelMacBook();
        verify(modelMapper).map(latestMacBook, LatestModelDeviceView.class);
    }

    @Test
    void findById() {
        when(macBookRepository.findById(anyLong())).thenReturn(Optional.of(macBook));

        MacBook result = macBookService.findById(1L);

        assertNotNull(result);
        assertEquals(macBook.getModel(), result.getModel());
    }

    @Test
    void findMacBookOwner() {
        User user = new User();
        user.setUsername("testUsername");

        MacBook macBook = new MacBook();
        macBook.setOwner(user);

        when(macBookRepository.findById(anyLong())).thenReturn(Optional.of(macBook));
        User result = macBookService.findMacBookOwner(1L);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void availableMacBooks() {
        when(macBookRepository.count()).thenReturn(1L);

        boolean result = macBookService.availableMacBooks();

        assertTrue(result);
    }

    @Test
    void saveDevice() {
        Device device = new Iphone();
        when(modelMapper.map(device, MacBook.class)).thenReturn(macBook);

        macBookService.saveDevice(device);

        verify(macBookRepository).save(macBook);
    }

    @Test
    void findMyMacBooks() {
        DeviceView deviceView = new DeviceView()
                .setModel("MacBook Pro 15")
                .setPrice(ModelAttributeUtil.formatPrice(macBook.getPrice()))
                .setDateOfPurchase(ModelAttributeUtil.formatDateWithoutTime(macBook.getDateOfPurchase()))
                .setType("macBook");
        user.setMyMacBooks(Collections.singletonList(macBook));
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(macBook, DeviceView.class)).thenReturn(deviceView);

        List<DeviceView> result = macBookService.findMyMacBooks("testUsername");

        assertFalse(result.isEmpty());
        assertEquals(deviceView.getModel(), result.get(0).getModel());
        assertEquals(deviceView.getPrice(), result.get(0).getPrice());
    }

    @Test
    void refreshMacBook() {
        MacBook macBook = new MacBook();
        macBook.setDateOfRegister(LocalDateTime.of(2018, 1, 1, 0, 0));

        when(macBookRepository.findById(1L)).thenReturn(Optional.of(macBook));

        macBookService.refreshMacBook(1L);

        verify(macBookRepository).findById(1L);
        verify(macBookRepository).save(macBook);
        assertTrue(macBook.getDateOfRegister().isAfter(LocalDateTime.of(2018, 1, 1, 0, 0)));
    }

    @Test
    void createMacBookProfileView() {
        macBook.setDateOfRegister(LocalDateTime.of(2021, 10, 10, 12, 0));

        MacBookProfileView macBookProfileView = new MacBookProfileView();
        macBookProfileView.setModel("MacBook Pro 15");
        macBookProfileView.setDateOfRegister(ModelAttributeUtil.formatDate(macBook.getDateOfRegister()));
        macBookProfileView.setPrice(ModelAttributeUtil.formatPrice(macBook.getPrice()));

        when(modelMapper.map(macBook, MacBookProfileView.class)).thenReturn(macBookProfileView);

        MacBookProfileView result = macBookService.createMacBookProfileView(macBook);

        assertNotNull(result);
        assertEquals(macBook.getModel(), result.getModel());
        assertEquals(ModelAttributeUtil.formatDate(macBook.getDateOfRegister()), result.getDateOfRegister());
        assertEquals(ModelAttributeUtil.formatPrice(macBook.getPrice()), result.getPrice());

        verify(modelMapper).map(macBook, MacBookProfileView.class);
    }

    @Test
    void deleteMacBook() {
        user.getMyMacBooks().add(macBook);
        doNothing().when(macBookRepository).deleteById(anyLong());

        macBookService.deleteMacBook(user, 1L, macBook);

        assertFalse(user.getMyMacBooks().contains(macBook));
        verify(macBookRepository).deleteById(1L);
    }
}