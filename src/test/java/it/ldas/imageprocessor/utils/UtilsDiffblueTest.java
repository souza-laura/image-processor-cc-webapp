package it.ldas.imageprocessor.utils;


import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Utils.class})
@ExtendWith(SpringExtension.class)
class UtilsDiffblueTest {
    @Autowired
    private Utils utils;

    /**
     * Test {@link Utils#isImageTypeValid(String)}.
     * <p>
     * Method under test: {@link Utils#isImageTypeValid(String)}
     */
    @Test
    @DisplayName("Test isImageTypeValid(String)")
    void testIsImageTypeValid() {
        // Arrange
        // TODO: Populate arranged inputs
        String imgType = "";

        // Act
        boolean actualIsImageTypeValidResult = Utils.isImageTypeValid(imgType);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Test {@link Utils#parseMetadata(String)}.
     * <p>
     * Method under test: {@link Utils#parseMetadata(String)}
     */
    @Test
    @DisplayName("Test parseMetadata(String)")
    void testParseMetadata() {
        // Arrange
        // TODO: Populate arranged inputs
        String metadata = "";

        // Act
        Map<String, Object> actualParseMetadataResult = Utils.parseMetadata(metadata);

        // Assert
        // TODO: Add assertions on result
    }
}
