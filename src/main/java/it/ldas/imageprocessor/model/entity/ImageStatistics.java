package it.ldas.imageprocessor.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Subselect("SELECT * FROM image_statistics_view")
@Synchronize("images")
public class ImageStatistics {

    @Id
    private Long id;
    private Integer totalImages;
    private Long totalSize;
    private Long totalPixels;
    private Integer receivedImages;
    private Integer processingImages;
    private Integer processedImages;
    private Integer failedImages;

}
