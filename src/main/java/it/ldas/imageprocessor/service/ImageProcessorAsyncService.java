package it.ldas.imageprocessor.service;

import it.ldas.imageprocessor.model.entity.Image;
import it.ldas.imageprocessor.repository.ImageRepository;
import it.ldas.imageprocessor.utils.enums.ElaborationState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageProcessorAsyncService {

    private static final Logger logger = LoggerFactory.getLogger(ImageProcessorAsyncService.class);

    @Autowired
    private ImageRepository imageRepository;

    @Async("taskExecutor")
    public void processImage(Long imageId, String dir) {
        // recupero ogg immagine da db
        logger.info("Starting processing for imageId: {}, directory: {}", imageId, dir);

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> {
                    logger.error("Image not found in db for imageId: {}", imageId);
                    return new RuntimeException("Image not found in db");
                });

        image.setElaborationState(ElaborationState.PROCESSING);
        imageRepository.save(image);

        logger.info("Image set to PROCESSING state for imageId: {}", imageId);
        // estrazione metadati
        try {

            String metadata = getMetadataImageMagick(dir);
            image.setMetadata(metadata);
            Map<String, Long> widthHeighMap = getImageDimensions(dir);
            image.setWidth(widthHeighMap.get("width"));
            image.setHeight(widthHeighMap.get("height"));

            logger.info("Metadata extracted for imageId: {}", imageId);

            createTransformedVersions(dir);

            logger.info("Transformed versions created for imageId: {}", imageId);

            image.setElaborationState(ElaborationState.PROCESSED);
            imageRepository.save(image);

            logger.info("Image set to PROCESSED state for imageId: {}", imageId);

        } catch (Exception e) {
            logger.error("Error processing imageId: {}", imageId, e);

            image.setElaborationState(ElaborationState.FAILED);
            imageRepository.save(image);

            logger.info("Image set to FAILED state for imageId: {}", imageId);
            throw new RuntimeException("Image metadata extraction and transformation failed", e);
        }
    }


    private String getMetadataImageMagick(String filePath) {
        try {
            // comando imagemagick per tutti i metadata
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

    private Map<String, Long> getImageDimensions(String filePath) {
        try {
            // comando imagemagick per larghezza e altezza
            ProcessBuilder processBuilder = new ProcessBuilder("identify", "-format", "%wx%h", filePath);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            process.waitFor();

            if (line != null) {
                String[] dimensions = line.split("x");
                if (dimensions.length == 2) {
                    Map<String, Long> dimensionsMap = new HashMap<>();
                    dimensionsMap.put("width", Long.valueOf(dimensions[0]));
                    dimensionsMap.put("height", Long.valueOf(dimensions[1]));
                    return dimensionsMap;
                }
            }
            throw new RuntimeException("Failed to extract width and height from image");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to extract image dimensions", e);
        }
    }

    private void createTransformedVersions(String filePath) {
        try {

            String extension = filePath.substring(filePath.lastIndexOf("."));
            String baseName = filePath.replace(extension, "");

            // trasformazione 50% dimensione
            String resized50Path = baseName + "_resized50" + extension;
            ProcessBuilder resize50Builder = new ProcessBuilder("convert", filePath, "-resize", "50%", resized50Path);
            Process resize50Process = resize50Builder.start();
            resize50Process.waitFor();

            // b&w
            String grayscalePath = baseName + "_grayscale" + extension;
            ProcessBuilder grayscaleBuilder = new ProcessBuilder("convert", filePath, "-colorspace", "gray", grayscalePath);
            Process grayscaleProcess = grayscaleBuilder.start();
            grayscaleProcess.waitFor();

            // jpg -> png, png -> jpg
            String alternateFormatPath = baseName + (extension.equals(".jpg") ? ".png" : ".jpg");
            ProcessBuilder formatBuilder = new ProcessBuilder("convert", filePath, alternateFormatPath);
            Process formatProcess = formatBuilder.start();
            formatProcess.waitFor();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to create transformed version", e);
        }
    }
}
