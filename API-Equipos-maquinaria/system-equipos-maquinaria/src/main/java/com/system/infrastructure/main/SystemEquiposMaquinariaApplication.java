package com.system.infrastructure.main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {
		"com.system.infrastructure",
		"com.system.modules",
		"com.system.crosscutting"})
@EnableJpaRepositories(basePackages = {
		"com.system.crosscutting.persistence.repository"})
@EntityScan(basePackages = "com.system.crosscutting.persistence.entity")
@EnableWebMvc
@EnableDiscoveryClient
public class SystemEquiposMaquinariaApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SystemEquiposMaquinariaApplication.class, args);
	}

}
