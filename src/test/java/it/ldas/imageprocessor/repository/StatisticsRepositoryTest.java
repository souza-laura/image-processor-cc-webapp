package it.ldas.imageprocessor.repository;

import it.ldas.imageprocessor.model.entity.ImageStatistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {StatisticsRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"it.ldas.imageprocessor.model.entity"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class StatisticsRepositoryTest {
    @Autowired
    private StatisticsRepository statisticsRepository;

    /**
     * Test {@link StatisticsRepository#getImageStats()}.
     * <p>
     * Method under test: {@link StatisticsRepository#getImageStats()}
     */
    @Test
    @DisplayName("Test getImageStats()")
    void testGetImageStats() {
        // Arrange and Act
        // TODO: Populate arranged inputs
        ImageStatistics actualImageStats = this.statisticsRepository.getImageStats();

        // Assert
        // TODO: Add assertions on result
    }
}
