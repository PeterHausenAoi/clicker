package com.PeterHausen.Clicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ClickerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClickerApplication.class, args);
	}

}
