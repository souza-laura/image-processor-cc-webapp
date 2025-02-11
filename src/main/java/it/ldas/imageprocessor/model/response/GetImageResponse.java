package it.ldas.imageprocessor.model.response;


import it.ldas.imageprocessor.utils.enums.ElaborationState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetImageResponse {

    private String originalName;
    private Long byteSize;
    private Map<String, Object> metadata;
    private ElaborationState elaborationState;

}
