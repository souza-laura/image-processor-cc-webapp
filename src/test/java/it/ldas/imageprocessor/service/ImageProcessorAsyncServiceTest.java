package it.ldas.imageprocessor.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ImageProcessorAsyncService.class})
@ExtendWith(SpringExtension.class)
class ImageProcessorAsyncServiceTest {
    @Autowired
    private ImageProcessorAsyncService imageProcessorAsyncService;

    /**
     * Test {@link ImageProcessorAsyncService#processImage(Long, String)}.
     * <p>
     * Method under test: {@link ImageProcessorAsyncService#processImage(Long, String)}
     */
    @Test
    @DisplayName("Test processImage(Long, String)")
    void testProcessImage() {
        // Arrange
        // TODO: Populate arranged inputs
        Long imageId = null;
        String dir = "";

        // Act
        this.imageProcessorAsyncService.processImage(imageId, dir);

        // Assert
        // TODO: Add assertions on result
    }
}
