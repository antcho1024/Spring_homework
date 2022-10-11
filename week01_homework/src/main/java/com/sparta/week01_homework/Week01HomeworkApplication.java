package com.sparta.week01_homework;

import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.service.BoardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Week01HomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(Week01HomeworkApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(BoardRepository repository, BoardService boardService) {
		return (args) -> {

		};
	}
}
