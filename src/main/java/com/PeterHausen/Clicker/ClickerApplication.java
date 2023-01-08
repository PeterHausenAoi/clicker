package com.PeterHausen.Clicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClickerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClickerApplication.class, args);
	}

}
