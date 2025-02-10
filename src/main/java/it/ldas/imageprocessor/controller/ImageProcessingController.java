package it.ldas.imageprocessor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.ldas.imageprocessor.dto.model.Image;
import it.ldas.imageprocessor.dto.response.GetImageResponse;
import it.ldas.imageprocessor.dto.response.StatsResponse;
import it.ldas.imageprocessor.service.ImageProcessorService;
import it.ldas.imageprocessor.utils.Utils;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/images")
@Tag(name = "Image API", description = "API per caricare, elaborare e gestire immagini")
public class ImageProcessingController {

    @Autowired
    private ImageProcessorService imageService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "carica un'immagine", description = "riceve un'immagine e salva informazioni")
    @ApiResponse(responseCode = "200", description = "Immagine caricata con successo")
    public ResponseEntity<String> uploadImage(@Parameter(description = "Immagine da caricare", required = true)
                                                  @RequestParam("file") MultipartFile image) {
        String fileType = image.getContentType();
        if (!Utils.isImageTypeValid(fileType))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il file deve essere di tipo PNG o JPG.");



        return ResponseEntity.ok("Immagine caricata con successo.");
    }

    @GetMapping("/image/{id}")
    @Operation(summary = "Dettagli immagine", description = "restituisce dettagli di un'immagine da id immagine")
    @ApiResponse(responseCode = "200", description = "Dettagli dell'immagine trovati")
    @ApiResponse(responseCode = "404", description = "Immagine non trovata")
    public ResponseEntity<GetImageResponse> getImage(@Parameter(description = "ID dell'immagine", required = true) @PathVariable Long id) {


        return null;
    }

    @GetMapping("/stats")
    @Operation(summary = "Dati statistici", description = "Restituisce le statistiche delle immagini caricate")
    @ApiResponse(responseCode = "200", description = "Statistiche calcolate con successo")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = imageService.getStatistics();
        return ResponseEntity.ok(stats);
    }




}
