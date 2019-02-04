package com.bob.alt;

import com.bob.alt.logging.Logger;
import com.bob.alt.logging.LoggerSelector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class Application {

    @Value("${ipfront}")
    private String ipfront;

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ipfront)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                        .allowCredentials(false)
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public Logger logger(@Value("${logType}") String logType,
                         @Value("${pathDir}") String pathDir,
                         @Value("${fileName}") String fileName,
                         @Value("${fileQuality}") int fileQuality,
                         @Value("${fileSize}") long fileSize) {
        return new LoggerSelector().log(logType, pathDir, fileName, fileQuality, fileSize);
    }

}

