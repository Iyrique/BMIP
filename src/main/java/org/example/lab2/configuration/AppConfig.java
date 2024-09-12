package org.example.lab2.configuration;

import org.example.lab2.generator.UserGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserGenerator userGenerator() {
        return new UserGenerator();
    }
}
