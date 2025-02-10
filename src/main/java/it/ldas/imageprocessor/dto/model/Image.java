package it.ldas.imageprocessor.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.ldas.imageprocessor.utils.enums.ElaborationState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private BigDecimal byteSize;
    private String directory;
    private String status;
    private BigDecimal width;
    private BigDecimal height;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Enumerated(EnumType.STRING)
    private ElaborationState elaborationState;

    @JsonIgnore
    public Map<String, String> getMetadataMap() {
        try {
            return new ObjectMapper().readValue(metadata, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Errore parsing JSON metadata", e);
        }
    }

    public void setMetadataMap(Map<String, String> metadataMap) {
        try {
            this.metadata = new ObjectMapper().writeValueAsString(metadataMap);
        } catch (IOException e) {
            throw new RuntimeException("Errore serializzazione JSON metadata", e);
        }
    }

}
