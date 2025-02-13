package it.ldas.imageprocessor.mapper;

import it.ldas.imageprocessor.model.entity.ImageStatistics;
import it.ldas.imageprocessor.model.response.StatsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StatisticsMapperTest {

    @InjectMocks
    private StatisticsMapper statisticsMapper = Mappers.getMapper(StatisticsMapper.class);

    @Test
    public void testToResponse() {
        ImageStatistics model = new ImageStatistics();
        model.setTotalImages(45);
        model.setFailedImages(12);
        model.setProcessedImages(12);
        model.setProcessingImages(6);
        model.setReceivedImages(15);

        StatsResponse expectedResponse = new StatsResponse();
        expectedResponse.setTotalImages(45);
        expectedResponse.setFailedImages(12);
        expectedResponse.setProcessedImages(12);
        expectedResponse.setProcessingImages(6);
        expectedResponse.setReceivedImages(15);

        StatsResponse actualResponse = statisticsMapper.toResponse(model);

        assertEquals(expectedResponse.getTotalImages(), actualResponse.getTotalImages());
        assertEquals(expectedResponse.getFailedImages(), actualResponse.getFailedImages());
        assertEquals(expectedResponse.getReceivedImages(), actualResponse.getReceivedImages());
    }
}

