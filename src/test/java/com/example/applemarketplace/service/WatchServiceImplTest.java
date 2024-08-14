package com.example.applemarketplace.service;

import com.example.applemarketplace.model.dtos.WatchAddDTO;
import com.example.applemarketplace.model.entity.*;
import com.example.applemarketplace.model.enums.CapacityRam;
import com.example.applemarketplace.model.enums.Colour;
import com.example.applemarketplace.model.enums.Display;
import com.example.applemarketplace.model.enums.InternalMemory;
import com.example.applemarketplace.model.view.DeviceView;
import com.example.applemarketplace.model.view.LatestModelDeviceView;
import com.example.applemarketplace.model.view.ModelsWithLargestMemoryView;
import com.example.applemarketplace.model.view.WatchProfileView;
import com.example.applemarketplace.repository.WatchRepository;
import com.example.applemarketplace.service.interfaces.UserService;
import com.example.applemarketplace.util.ModelAttributeUtil;
import org.assertj.core.api.AssertionsForClassTypes;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.applemarketplace.model.enums.InternalMemory.GB_512;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserService userService;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private WatchRepository watchRepository;
    @InjectMocks
    private WatchServiceImpl watchService;
    @Captor
    private ArgumentCaptor<User> userCaptor;
    private WatchAddDTO watchAddDTO;
    private Watch watch;
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

        watchAddDTO = new WatchAddDTO()
                .setModel("Apple Watch Series 8")
                .setDateOfPurchase(LocalDate.now())
                .setPhotosUrls(List.of(photo1, photo2));

        watch = new Watch();
        watch.setStatusDevice("New");
        watch.setWarranty(24);
        watch.setModel("Apple Watch Series 7");
        watch.setResolution("396 x 484 pixels");
        watch.setProcessor("S8 SiP");
        watch.setCapacityRam(CapacityRam.GB_1);
        watch.setOperatingSystem("watchOS 9");
        watch.setDisplaySize("45 mm");
        watch.setColour(Colour.SPACE_GRAY);
        watch.setDateOfPurchase(LocalDate.now());
        watch.setPrice(BigDecimal.valueOf(500.00));
        watch.setPhotosUrls(List.of("uploaded_photo1_url", "uploaded_photo2_url"));
        watch.setDateOfRegister(LocalDateTime.now());
        watch.setDisplay(Display.LIQUID_RETINA_HD);
        watch.setInternalMemory(InternalMemory.GB_32);
        watch.setSizes("45 x 38 x 10.7 mm");
        watch.setOwner(user);

        userDetails = mock(UserDetails.class);
        lenient().when(userDetails.getUsername()).thenReturn("testUsername");
        lenient().when(cloudinaryService.saveImage(photo1)).thenReturn("uploaded_photo1_url");
        lenient().when(cloudinaryService.saveImage(photo2)).thenReturn("uploaded_photo2_url");
    }

    @Test
    void saveWatch() {
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(watchAddDTO, Watch.class)).thenReturn(watch);

        watchService.saveWatch(watchAddDTO, userDetails);
        verify(userService).saveCurrentUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertTrue(capturedUser.getMyWatches().contains(watch));

        Watch capturedWatch = capturedUser.getMyWatches().get(0);
        assertEquals(watch.getModel(), capturedWatch.getModel());
        assertEquals(watch.getPrice(), capturedWatch.getPrice());
        assertEquals(watch.getDateOfPurchase(), capturedWatch.getDateOfPurchase());
        assertEquals(watch.getPhotosUrls(), capturedWatch.getPhotosUrls());
    }

    @Test
    void latestModelWatch() {
        Watch latestWatch = new Watch();
        latestWatch.setModel("Apple Watch Series 8");

        LatestModelDeviceView expectedView = new LatestModelDeviceView();
        expectedView.setModel("Apple Watch Series 8");

        when(watchRepository.findLatestModelWatch()).thenReturn(List.of(latestWatch));
        when(modelMapper.map(latestWatch, LatestModelDeviceView.class)).thenReturn(expectedView);

        LatestModelDeviceView result = watchService.latestModelWatch();

        assertThat(result).isNotNull();
        assertThat(result.getModel()).isEqualTo("Apple Watch Series 8");

        verify(watchRepository).findLatestModelWatch();
        verify(modelMapper).map(latestWatch, LatestModelDeviceView.class);
    }

    @Test
    void findById() {
        when(watchRepository.findById(anyLong())).thenReturn(Optional.of(watch));

        Watch result = watchService.findById(1L);

        assertNotNull(result);
        assertEquals(watch.getModel(), result.getModel());
    }

    @Test
    void createWatchProfileView() {
        WatchProfileView expectedView = new WatchProfileView();
        expectedView.setModel("Apple Watch Series 7");
        expectedView.setDateOfPurchase(ModelAttributeUtil.formatDateWithoutTime(watch.getDateOfPurchase()));
        expectedView.setDateOfRegister(ModelAttributeUtil.formatDate(watch.getDateOfRegister()));
        expectedView.setPrice(ModelAttributeUtil.formatPrice(watch.getPrice()));

        when(modelMapper.map(watch, WatchProfileView.class)).thenReturn(expectedView);

        WatchProfileView result = watchService.createWatchProfileView(watch);

        assertNotNull(result);
        assertEquals(expectedView.getModel(), result.getModel());
        assertEquals(expectedView.getDateOfPurchase(), result.getDateOfPurchase());
        assertEquals(expectedView.getDateOfRegister(), result.getDateOfRegister());
        assertEquals(expectedView.getPrice(), result.getPrice());

        verify(modelMapper).map(watch, WatchProfileView.class);
    }


    @Test
    void findLongestWarrantyWatch() {
        Watch longWarrantyWatch = new Watch();
        longWarrantyWatch.setModel("Apple Watch Series 6");
        longWarrantyWatch.setDateOfPurchase(LocalDate.now().minusYears(2));
        longWarrantyWatch.setPrice(BigDecimal.valueOf(399.99));

        DeviceView expectedView = new DeviceView();
        expectedView.setModel("Apple Watch Series 6");
        expectedView.setDateOfPurchase("2022-08-14");
        expectedView.setPrice("399.99");
        expectedView.setType("watch");

        when(watchRepository.findLongestWarrantyWatch()).thenReturn(List.of(longWarrantyWatch));
        when(modelMapper.map(longWarrantyWatch, DeviceView.class)).thenReturn(expectedView);

        List<DeviceView> result = watchService.findLongestWarrantyWatch();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        DeviceView resultView = result.get(0);
        assertEquals("Apple Watch Series 6", resultView.getModel());
        assertEquals("2022-08-14", resultView.getDateOfPurchase());
        assertEquals("399.99", resultView.getPrice());
        assertEquals("watch", resultView.getType());

        verify(watchRepository).findLongestWarrantyWatch();
        verify(modelMapper).map(longWarrantyWatch, DeviceView.class);
    }

    @Test
    void deleteWatch() {
        user.getMyWatches().add(watch);
        doNothing().when(watchRepository).deleteById(anyLong());

        watchService.deleteWatch(user, 1L, watch);

        assertFalse(user.getMyWatches().contains(watch));
        verify(watchRepository).deleteById(1L);
    }

    @Test
    void refreshWatch() {
        Watch watchToRefresh = new Watch();
        watchToRefresh.setDateOfRegister(LocalDateTime.of(2020, 1, 1, 0, 0));

        when(watchRepository.findById(1L)).thenReturn(Optional.of(watchToRefresh));

        watchService.refreshWatch(1L);

        verify(watchRepository).findById(1L);
        verify(watchRepository).save(watchToRefresh);
        assertTrue(watchToRefresh.getDateOfRegister().isAfter(LocalDateTime.of(2020, 1, 1, 0, 0)));
    }

    @Test
    void findMyWatches() {
        DeviceView deviceView = new DeviceView()
                .setModel("Apple Watch Series 7")
                .setPrice(ModelAttributeUtil.formatPrice(watch.getPrice()))
                .setDateOfPurchase(ModelAttributeUtil.formatDateWithoutTime(watch.getDateOfPurchase()))
                .setType("watch");

        user.setMyWatches(Collections.singletonList(watch));
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(watch, DeviceView.class)).thenReturn(deviceView);

        List<DeviceView> result = watchService.findMyWatches("testUsername");

        assertFalse(result.isEmpty());
        assertEquals(deviceView.getModel(), result.get(0).getModel());
        assertEquals(deviceView.getPrice(), result.get(0).getPrice());
    }
}
