package com.vgeorgo.projectorganizer;

import com.vgeorgo.projectorganizer.support.repositories.CustomRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {"com.vgeorgo.projectorganizer"})
@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = CustomRepository.class)
public class ProjectorganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectorganizerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:8080")
					.allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}
