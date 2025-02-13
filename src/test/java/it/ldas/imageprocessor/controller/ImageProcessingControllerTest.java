package it.ldas.imageprocessor.controller;

import it.ldas.imageprocessor.model.response.GetImageResponse;
import it.ldas.imageprocessor.model.response.ImageUploadResponse;
import it.ldas.imageprocessor.model.response.StatsResponse;
import it.ldas.imageprocessor.service.ImageProcessorService;

import java.math.BigDecimal;
import java.util.List;

import it.ldas.imageprocessor.utils.enums.ElaborationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {ImageProcessingController.class, ImageProcessorService.class})
@ExtendWith(SpringExtension.class)
class ImageProcessingControllerTest {

    @Autowired
    private ImageProcessingController imageProcessingController;

    @MockitoBean
    private ImageProcessorService imageProcessorService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageProcessingController).build();
    }

    /**
     * Test {@link ImageProcessingController#uploadImage(List)}.
     */
    @Test
    void testUploadImage_OK() throws Exception {
        MockMultipartFile file = new MockMultipartFile("images", "file.png", "image/png",
                "image content".getBytes());

        List<ImageUploadResponse> mockResponse = List.of(new ImageUploadResponse(1L, "dymmyName.png", "/dummy_dir"));
        when(imageProcessorService.uploadImages(anyList())).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/images/upload")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].originalName").value("dymmyName.png"))
                .andExpect(jsonPath("$[0].path").value("/dummy_dir"));
    }

    @Test
    void testUploadImage_KO_FileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile("images", "file.txt", "text/plain",
                "invalid content".getBytes());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/images/upload")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        result.andExpect(status().isBadRequest())
                .andExpect(status().reason("Tutti i file devono essere di tipo PNG o JPG."));
    }


    @Test
    void testGetImageData_OK() throws Exception {
        GetImageResponse mockResponse = new GetImageResponse("dummyName.png",12345L, null, ElaborationState.PROCESSED);
        when(imageProcessorService.retrieveImageData(1L)).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/images/image/{id}", 1L));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.originalName").value("dummyName.png"));
    }

    @Test
    void testGetImageData_KO_NotFound() throws Exception {
        when(imageProcessorService.retrieveImageData(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/images/image/{id}", 1L));

        result.andExpect(status().isNotFound());
    }

    @Test
    void testGetStats_OK() throws Exception {
        StatsResponse mockResponse = new StatsResponse(10, 10L, 10L, 5, 0, 0, 5);
        when(imageProcessorService.getStatistics()).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/images/stats"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalImages").value(10));
    }
}
