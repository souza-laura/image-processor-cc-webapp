package it.ldas.imageprocessor.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    private Integer totalImages;
    private Long totalSize;
    private Long totalPixels;
    private Integer receivedImages;
    private Integer processingImages;
    private Integer processedImages;
    private Integer failedImages;
}
