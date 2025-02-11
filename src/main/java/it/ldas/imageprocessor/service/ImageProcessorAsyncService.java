package it.ldas.imageprocessor.service;

import it.ldas.imageprocessor.model.entity.Image;
import it.ldas.imageprocessor.repository.ImageRepository;
import it.ldas.imageprocessor.utils.enums.ElaborationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ImageProcessorAsyncService {

    @Autowired
    private ImageRepository imageRepository;

    @Async("taskExecutor")
    public void processImage(Long imageId, String dir) {
        // recupero ogg immagine da db
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not foud in db"));

        image.setElaborationState(ElaborationState.PROCESSING);
        imageRepository.save(image);
        // estrazione metadati
        try {
            String metadata = getMetadataImageMagick(dir);
            image.setMetadata(metadata);
            // todo trasformazione (?)
            image.setElaborationState(ElaborationState.PROCESSED);
            imageRepository.save(image);
            image.getMetadataMap();
        } catch (Exception e) {
            image.setElaborationState(ElaborationState.FAILED);
            imageRepository.save(image);
            throw new RuntimeException("Image metadata extraction and transformation failed", e);
        }
    }


    private String getMetadataImageMagick(String filePath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("identify", "-verbose", filePath);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            return output.toString();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to extract metadata", e);
        }
    }
}
