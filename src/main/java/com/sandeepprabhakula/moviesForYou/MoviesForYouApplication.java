package com.sandeepprabhakula.moviesForYou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MoviesForYouApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesForYouApplication.class, args);
	}

}
