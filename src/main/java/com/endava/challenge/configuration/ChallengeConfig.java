package com.endava.challenge.configuration;

import com.endava.challenge.repository.MovieRepository;
import com.endava.challenge.repository.RatingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {MovieRepository.class, RatingRepository.class})
@Configuration
public class ChallengeConfig {
    @Bean
    public LoggingEventListener mongoEventListener() {
        return new LoggingEventListener();
    }
}
