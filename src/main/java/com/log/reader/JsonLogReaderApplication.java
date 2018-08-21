package com.log.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //Used to enable async operation
public class JsonLogReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonLogReaderApplication.class, args);
	}

}
