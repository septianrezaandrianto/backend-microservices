package com.service.beconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class BeConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeConfigServerApplication.class, args);
	}

}
