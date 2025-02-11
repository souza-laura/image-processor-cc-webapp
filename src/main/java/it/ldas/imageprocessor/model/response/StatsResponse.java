package it.ldas.imageprocessor.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StatsResponse {
    private Integer totalImages;
    private BigDecimal totalSize;
    private BigDecimal totalPixels;
    private Integer receivedImages;
    private Integer processingImages;
    private Integer processedImages;
}
