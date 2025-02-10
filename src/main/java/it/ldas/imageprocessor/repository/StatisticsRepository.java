package it.ldas.imageprocessor.repository;

import io.swagger.v3.oas.annotations.Hidden;
import it.ldas.imageprocessor.dto.model.Image;
import it.ldas.imageprocessor.dto.model.ImageStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface StatisticsRepository extends JpaRepository<ImageStatistics, Integer> {

    @Query(value = "SELECT * FROM image_statistics", nativeQuery = true)
    ImageStatistics getImageStats();
}
