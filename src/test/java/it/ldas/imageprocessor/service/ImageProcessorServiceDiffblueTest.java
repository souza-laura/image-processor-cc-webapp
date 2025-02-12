package it.ldas.imageprocessor.service;

import it.ldas.imageprocessor.model.response.GetImageResponse;
import it.ldas.imageprocessor.model.response.ImageUploadResponse;
import it.ldas.imageprocessor.model.response.StatsResponse;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ImageProcessorService.class})
@ExtendWith(SpringExtension.class)
class ImageProcessorServiceDiffblueTest {
    @Autowired
    private ImageProcessorService imageProcessorService;

    /**
     * Test {@link ImageProcessorService#uploadImages(List)}.
     * <p>
     * Method under test: {@link ImageProcessorService#uploadImages(List)}
     */
    @Test
    @DisplayName("Test uploadImages(List)")
    void testUploadImages() {
        // Arrange
        // TODO: Populate arranged inputs
        List<MultipartFile> images = null;

        // Act
        List<ImageUploadResponse> actualUploadImagesResult = this.imageProcessorService.uploadImages(images);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Test {@link ImageProcessorService#retrieveImageData(Long)}.
     * <p>
     * Method under test: {@link ImageProcessorService#retrieveImageData(Long)}
     */
    @Test
    @DisplayName("Test retrieveImageData(Long)")
    void testRetrieveImageData() {
        // Arrange
        // TODO: Populate arranged inputs
        Long id = null;

        // Act
        GetImageResponse actualRetrieveImageDataResult = this.imageProcessorService.retrieveImageData(id);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Test {@link ImageProcessorService#getStatistics()}.
     * <p>
     * Method under test: {@link ImageProcessorService#getStatistics()}
     */
    @Test
    @DisplayName("Test getStatistics()")
    void testGetStatistics() {
        // Arrange and Act
        // TODO: Populate arranged inputs
        StatsResponse actualStatistics = this.imageProcessorService.getStatistics();

        // Assert
        // TODO: Add assertions on result
    }
}
