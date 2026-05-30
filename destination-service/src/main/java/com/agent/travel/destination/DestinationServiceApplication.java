package com.agent.travel.destination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.agent.travel")
@EnableDiscoveryClient
@EntityScan("com.agent.travel.model")
@EnableJpaRepositories("com.agent.travel.repository")
public class DestinationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DestinationServiceApplication.class, args);
    }
}
