package it.ldas.imageprocessor.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.ldas.imageprocessor.utils.Utils;
import it.ldas.imageprocessor.utils.enums.ElaborationState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
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
    private Long byteSize;
    private String directory;
    private Long width;
    private Long height;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Enumerated(EnumType.STRING)
    private ElaborationState elaborationState;

    @JsonIgnore
    public Map<String, Object> getMetadataMap() {
        return Utils.parseMetadata(this.metadata);

    }

}
