package io.github.julianjupiter.springbootmanytomany;

import io.github.julianjupiter.springbootmanytomany.config.FileStorageProperties;
import io.github.julianjupiter.springbootmanytomany.service.StorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class SpringBootJpaManyToManyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaManyToManyApplication.class, args);
	}

	@Bean
	CommandLineRunner init(@Qualifier("screenshotStorageService") StorageService screenshotStorageService,
						   @Qualifier("videoStorageService") StorageService videoStorageService) {
		return (args) -> {
			screenshotStorageService.init();
			videoStorageService.init();
		};
	}

}

