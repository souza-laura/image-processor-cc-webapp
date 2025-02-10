package it.ldas.imageprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ImageProcessorCcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageProcessorCcApplication.class, args);
	}

}
