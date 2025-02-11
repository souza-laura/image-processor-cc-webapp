package it.ldas.imageprocessor.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResponse {
    private Long id;
    private String originalName;
    private String path;
}
