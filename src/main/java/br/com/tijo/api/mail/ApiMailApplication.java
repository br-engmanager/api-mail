package br.com.tijo.api.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"br.com.tijo.api"})
public class ApiMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMailApplication.class, args);
	}

}
