package com.system.infrastructure.main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SystemConfigApplication {
	public static void main(final String[] args) {
		SpringApplication.run(SystemConfigApplication.class, args);
	}

}
