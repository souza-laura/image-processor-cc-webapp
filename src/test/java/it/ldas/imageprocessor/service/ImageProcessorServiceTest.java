package it.ldas.imageprocessor.service;

import it.ldas.imageprocessor.mapper.ImageMapper;
import it.ldas.imageprocessor.mapper.StatisticsMapper;
import it.ldas.imageprocessor.model.entity.Image;
import it.ldas.imageprocessor.model.entity.ImageStatistics;
import it.ldas.imageprocessor.model.response.GetImageResponse;
import it.ldas.imageprocessor.model.response.ImageUploadResponse;
import it.ldas.imageprocessor.model.response.StatsResponse;

import java.util.List;
import java.util.Optional;

import it.ldas.imageprocessor.repository.ImageRepository;
import it.ldas.imageprocessor.repository.StatisticsRepository;
import it.ldas.imageprocessor.utils.enums.ElaborationState;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ImageProcessorService.class})
@ExtendWith(SpringExtension.class)
class ImageProcessorServiceTest {
    @Autowired
    private ImageProcessorService imageProcessorService;

    @MockitoBean
    private ImageRepository imageRepository;

    @MockitoBean
    private ImageProcessorAsyncService asyncService;

    @MockitoBean
    private ImageMapper imageMapper;

    @MockitoBean
    private StatisticsRepository statisticsRepository;

    @MockitoBean
    private StatisticsMapper statisticsMapper;


    @Test
    void testUploadImages() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.jpg");
        when(mockFile.getSize()).thenReturn(1024L);

        List<MultipartFile> images = List.of(mockFile);

        Image savedImage = new Image();
        savedImage.setId(1L);
        savedImage.setOriginalName("test.jpg");
        savedImage.setByteSize(1024L);
        savedImage.setElaborationState(ElaborationState.RECEIVED);
        savedImage.setDirectory(System.getProperty("user.dir") + "/uploaded_images/1_test.jpg");

        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);

        List<ImageUploadResponse> result = imageProcessorService.uploadImages(images);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
        assertEquals("test.jpg", result.getFirst().getOriginalName());
        assertEquals(System.getProperty("user.dir") + "/uploaded_images/1_test.jpg", result.getFirst().getPath());

        verify(imageRepository, times(2)).save(any(Image.class));
        verify(asyncService, times(1)).processImage(eq(1L), eq(System.getProperty("user.dir") + "/uploaded_images/1_test.jpg"));
    }


    @Test
    void testRetrieveImageData() {
        Long id = 1L;
        Image imageData = new Image();
        imageData.setId(id);
        imageData.setOriginalName("test.jpg");
        imageData.setByteSize(1024L);
        imageData.setElaborationState(ElaborationState.PROCESSED);
        imageData.setDirectory("/uploaded_images/1_test.jpg");

        when(imageRepository.findById(id)).thenReturn(Optional.of(imageData));

        GetImageResponse mockResponse = new GetImageResponse();
        mockResponse.setOriginalName("test.jpg");

        when(imageMapper.toResponse(imageData)).thenReturn(mockResponse);

        GetImageResponse result = imageProcessorService.retrieveImageData(id);

        assertNotNull(result);
        assertEquals("test.jpg", result.getOriginalName());

        verify(imageRepository, times(1)).findById(id);
        verify(imageMapper, times(1)).toResponse(imageData);
    }

    @Test
    void testRetrieveImageData_NotFound() {
        Long id = 1L;
        when(imageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> imageProcessorService.retrieveImageData(id));

        verify(imageRepository, times(1)).findById(id);
    }


    @Test
    void testGetStatistics() {
        // Arrange
        ImageStatistics mockStats = new ImageStatistics();
        mockStats.setTotalImages(10);
        mockStats.setTotalSize(10240L);
        mockStats.setTotalPixels(1024034L);

        when(statisticsRepository.getImageStats()).thenReturn(mockStats);

        StatsResponse mockResponse = new StatsResponse();
        mockResponse.setTotalImages(10);
        mockResponse.setTotalSize(10240L);
        mockResponse.setTotalPixels(1024034L);

        when(statisticsMapper.toResponse(mockStats)).thenReturn(mockResponse);

        StatsResponse result = imageProcessorService.getStatistics();

        assertNotNull(result);
        assertEquals(10, result.getTotalImages());
        assertEquals(10240L, result.getTotalSize());
        assertEquals(1024034L, result.getTotalPixels());

        verify(statisticsRepository, times(1)).getImageStats();
        verify(statisticsMapper, times(1)).toResponse(mockStats);
    }
}
