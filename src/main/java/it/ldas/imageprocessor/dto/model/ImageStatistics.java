package it.ldas.imageprocessor.dto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Id  // id per hibernate
    private Long id;
    private Integer totalImages;
    private BigDecimal totalSize;
    private BigDecimal totalPixels;
    private Integer receivedImages;
    private Integer processingImages;
    private Integer processedImages;

}
