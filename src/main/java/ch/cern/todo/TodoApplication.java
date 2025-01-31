package ch.cern.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import ch.cern.todo.config.SecurityConfig;

@SpringBootApplication
@Import(SecurityConfig.class)
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
