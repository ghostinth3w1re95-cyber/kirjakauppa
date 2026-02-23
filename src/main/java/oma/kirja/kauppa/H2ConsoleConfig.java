package oma.kirja.kauppa;

import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * H2 Console Configuration
 * Starts H2 web console in a separate thread
 */
@Configuration
@Profile("!test")
public class H2ConsoleConfig {

    private static final Logger log = LoggerFactory.getLogger(H2ConsoleConfig.class);

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public Server h2Server() {
        // Try default port 8082, fall back to 9092 if port is in use.
        try {
            log.info("Starting H2 console on port 8082");
            return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        } catch (SQLException first) {
            log.warn("Failed to start H2 console on port 8082, trying fallback port 9092", first);
            try {
                return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "9092").start();
            } catch (SQLException second) {
                log.warn("Failed to start H2 console on fallback port 9092. H2 console will be disabled.", second);
                return null;
            }
        }
    }
}

