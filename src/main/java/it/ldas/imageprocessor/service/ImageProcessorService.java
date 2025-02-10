package it.ldas.imageprocessor.service;

import it.ldas.imageprocessor.dto.model.Image;
import it.ldas.imageprocessor.dto.response.GetImageResponse;
import it.ldas.imageprocessor.dto.response.StatsResponse;
import it.ldas.imageprocessor.repository.ImageRepository;
import it.ldas.imageprocessor.repository.StatisticsRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageProcessorService {

    //

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;



    public StatsResponse getStatistics() {
        return null;
    }

    public String uploadImage(MultipartFile image) {
        return "";
    }

    public GetImageResponse retrieveImageData(Long id) throws Exception {
        Image imageData = imageRepository.findById(id).orElse(null);

        if (ObjectUtils.isEmpty(imageData)) {
            throw new Exception("");
        }

        return null;
    }
}
