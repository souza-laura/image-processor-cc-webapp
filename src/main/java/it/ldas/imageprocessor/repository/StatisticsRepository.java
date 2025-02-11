package it.ldas.imageprocessor.repository;

import io.swagger.v3.oas.annotations.Hidden;
import it.ldas.imageprocessor.model.entity.ImageStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface StatisticsRepository extends JpaRepository<ImageStatistics, Integer> {

    @Query(value = "SELECT * FROM image_statistics_view", nativeQuery = true)
    ImageStatistics getImageStats();

}
