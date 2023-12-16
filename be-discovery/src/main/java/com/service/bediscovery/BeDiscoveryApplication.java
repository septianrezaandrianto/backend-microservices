package com.service.bediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class BeDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeDiscoveryApplication.class, args);
	}

}
