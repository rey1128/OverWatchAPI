package com.rey.overwatch;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.rey.overwatch.service.DataLoaderService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.rey" })
public class App {

	@Autowired
	private DataLoaderService dataSrv;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			dataSrv.initHeros();
			System.out.println("heros are saved");
			dataSrv.initAbilities();
			System.out.println("abilities are saved");
		};
	}

}
