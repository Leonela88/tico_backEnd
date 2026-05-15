package com.femcoders.tico;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import ch.martinelli.oss.testcontainers.mailpit.MailpitContainer;

@TestConfiguration(proxyBeanMethods = false)
public class DevContainers {
     @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:17");
        container.start();
        System.out.println(">>> PostgreSQL URL: " + container.getJdbcUrl());
        System.out.println(">>> PostgreSQL User: " + container.getUsername());
        System.out.println(">>> PostgreSQL Password: " + container.getPassword());
        return container;
    }

    @Bean
    @ServiceConnection
    MailpitContainer mailpitContainer() {
        return new MailpitContainer("axllent/mailpit");
    }
}
