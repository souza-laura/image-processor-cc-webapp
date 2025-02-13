package it.ldas.imageprocessor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Value("${image.processing.max-concurrent-tasks}")
    private int maxConcurrentTasks;

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(maxConcurrentTasks); // max elab parallele
        executor.setMaxPoolSize(maxConcurrentTasks);  // max thread
        executor.setQueueCapacity(10);               // capacità coda
        executor.initialize();
        return executor;
    }
}
