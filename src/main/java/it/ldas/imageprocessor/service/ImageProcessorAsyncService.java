package it.ldas.imageprocessor.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageProcessorAsyncService {

    @Async
    public void processImage(String imageId, MultipartFile file) {
        // Esegue ImageMagick per estrarre i metadati e trasformare l'immagine
        // Aggiorna lo stato dell'immagine nel database
    }
}
