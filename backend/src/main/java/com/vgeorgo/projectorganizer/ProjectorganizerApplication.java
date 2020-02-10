package com.vgeorgo.projectorganizer;

import com.vgeorgo.projectorganizer.support.repositories.CustomRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = CustomRepository.class)
public class ProjectorganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectorganizerApplication.class, args);
	}

}
