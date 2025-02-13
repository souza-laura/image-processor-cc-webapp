package it.ldas.imageprocessor.mapper;

import it.ldas.imageprocessor.model.entity.Image;
import it.ldas.imageprocessor.model.response.GetImageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageMapperTest {

    @InjectMocks
    private ImageMapper imageMapper = Mappers.getMapper(ImageMapper.class);

    @Mock
    private Image image;

    @Test
    public void testToResponse() {
        when(image.getMetadataMap()).thenReturn(Map.of("dummyMetadata", "dummyValue", "dummyMeta2", 12312));

        GetImageResponse expectedResponse = new GetImageResponse();
        expectedResponse.setMetadata(Map.of("dummyMetadata", "dummyValue", "dummyMeta2", 12312));

        GetImageResponse actualResponse = imageMapper.toResponse(image);

        assertEquals(expectedResponse.getMetadata(), actualResponse.getMetadata());
    }
}
