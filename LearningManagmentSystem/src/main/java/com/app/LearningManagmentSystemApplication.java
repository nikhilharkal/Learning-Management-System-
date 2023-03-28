package com.app;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class LearningManagmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningManagmentSystemApplication.class, args);
	}
	
	// add a method to configure ModelMapper bean as a spring bean (to be managed by
		// SC)
		@Bean
		public ModelMapper mapper() {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			return modelMapper;
		}
		
	
}
