package it.ldas.imageprocessor.repository;

import io.swagger.v3.oas.annotations.Hidden;
import it.ldas.imageprocessor.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ImageRepository extends JpaRepository<Image, Long> {
}
