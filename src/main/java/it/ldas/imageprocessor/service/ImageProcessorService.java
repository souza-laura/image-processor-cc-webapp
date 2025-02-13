package it.ldas.imageprocessor.service;

import it.ldas.imageprocessor.model.entity.Image;
import it.ldas.imageprocessor.model.response.GetImageResponse;
import it.ldas.imageprocessor.model.response.ImageUploadResponse;
import it.ldas.imageprocessor.model.response.StatsResponse;
import it.ldas.imageprocessor.mapper.ImageMapper;
import it.ldas.imageprocessor.mapper.StatisticsMapper;
import it.ldas.imageprocessor.repository.ImageRepository;
import it.ldas.imageprocessor.repository.StatisticsRepository;
import it.ldas.imageprocessor.utils.enums.ElaborationState;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(ImageProcessorService.class);

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private ImageProcessorAsyncService asyncService;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private ImageMapper imageMapper;


    public List<ImageUploadResponse> uploadImages(List<MultipartFile> images) {
        List<ImageUploadResponse> responseList = new ArrayList<>();
        for (MultipartFile toUpload : images) {
            // save in db
            Image image = new Image();
            image.setOriginalName(toUpload.getOriginalFilename());
            image.setByteSize(toUpload.getSize());
            image.setElaborationState(ElaborationState.RECEIVED);

            image = imageRepository.save(image);

            logger.debug("Image data saved on the db");
            // save in dir
            String filePath = System.getProperty("user.dir") + "/uploaded_images/" + image.getId() + "_" + toUpload.getOriginalFilename();
            try {
                toUpload.transferTo(new java.io.File(filePath));
                image.setDirectory(filePath);
                imageRepository.save(image);
                logger.info("Image correctly saved in directory");
            } catch (IOException e) {
                logger.error("Errors occurred while saving image in directory");
                throw new RuntimeException("Failed to save image in directory", e);
            }

            logger.debug("Starting to process image");
            asyncService.processImage(image.getId(), image.getDirectory());
            responseList.add(new ImageUploadResponse(image.getId(), image.getOriginalName(), image.getDirectory()));
        }
        return responseList;
    }


    public GetImageResponse retrieveImageData(Long id) {
        Image imageData = imageRepository.findById(id).orElse(null);

        if (ObjectUtils.isEmpty(imageData)) {
            throw new EntityNotFoundException("Image data not found for id " + id);
        }
        // todo mappare getImageRes
        return imageMapper.toResponse(imageData);
    }


    public StatsResponse getStatistics() {
        // todo aggiungere controllo se non ci sono dati stats (?)
        return statisticsMapper.toResponse(statisticsRepository.getImageStats());
    }


}
