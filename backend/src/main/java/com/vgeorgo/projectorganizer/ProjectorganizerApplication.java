package com.vgeorgo.projectorganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectorganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectorganizerApplication.class, args);
	}

}
