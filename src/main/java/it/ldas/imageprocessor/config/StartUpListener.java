package it.ldas.imageprocessor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class StartUpListener {

    private static final Logger logger = LoggerFactory.getLogger(StartUpListener.class);

    @Value("${image.upload.directory:uploaded_images}")
    private String uploadDirectory;

    @EventListener(ApplicationReadyEvent.class)
    public void verifyUploadDirectory() {

        File directory = new File(uploadDirectory);
        logger.info("Verifica directory: {}", directory.getAbsolutePath());

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                logger.info("Directory creata: {}", directory.getAbsolutePath());
            } else {
                logger.error("Impossibile creare la directory: {}", directory.getAbsolutePath());
            }
        } else {
            logger.info("La directory esiste già: {}", directory.getAbsolutePath());
        }
    }
}
