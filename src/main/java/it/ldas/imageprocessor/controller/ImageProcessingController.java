package it.ldas.imageprocessor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.ldas.imageprocessor.model.response.GetImageResponse;
import it.ldas.imageprocessor.model.response.ImageUploadResponse;
import it.ldas.imageprocessor.model.response.StatsResponse;
import it.ldas.imageprocessor.service.ImageProcessorService;
import it.ldas.imageprocessor.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/images")
@Tag(name = "Image Processing APIs", description = "APIs per caricare, elaborare e trasformare immagini")
public class ImageProcessingController {

    @Autowired
    private ImageProcessorService imageService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Carica una o più immagini", description = "riceve un'immagine e salva informazioni")
    @ApiResponse(responseCode = "200", description = "Immagine caricata con successo")
    public ResponseEntity<List<ImageUploadResponse>> uploadImage(@Parameter(description = "Immagini da caricare", required = true)
                                                                     @RequestParam("images") List<MultipartFile> imagesList) {

        boolean allValid = imagesList.stream()
                .map(MultipartFile::getContentType)
                .allMatch(Utils::isImageTypeValid);

        if (!allValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tutti i file devono essere di tipo PNG o JPG.");
        }

        List<ImageUploadResponse> response = imageService.uploadImages(imagesList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/image/{id}")
    @Operation(summary = "Dettagli immagine caricata", description = "Restituisce dettagli di un'immagine da id immagine")
    @ApiResponse(responseCode = "200", description = "Dettagli dell'immagine trovati")
    @ApiResponse(responseCode = "404", description = "Immagine non trovata")
    public ResponseEntity<GetImageResponse> getImageData(@Parameter(description = "ID dell'immagine", required = true)
                                                         @PathVariable Long id) {

        GetImageResponse response = imageService.retrieveImageData(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    @Operation(summary = "Dati statistici sul caricamento complessivo", description = "Restituisce le statistiche delle immagini caricate")
    @ApiResponse(responseCode = "200", description = "statistiche calcolate con successo")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse response = imageService.getStatistics();
        return ResponseEntity.ok(response);
    }




}
