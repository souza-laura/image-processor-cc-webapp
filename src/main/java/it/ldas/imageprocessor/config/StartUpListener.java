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

    @Value("${image.upload.directory}")
    private String uploadDirectory;

    @EventListener(ApplicationReadyEvent.class)
    public void createUploadDirectory() {
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
            logger.info("Directory creata: {}", uploadDirectory);
        }
    }
}
