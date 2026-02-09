package oma.kirja.kauppa;

import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

/**
 * H2 Console Configuration
 * Starts H2 web console in a separate thread
 */
@Configuration
@Profile("!test")
public class H2ConsoleConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public Server h2Server() throws SQLException {
        // Start H2 console on port 8082 (default: 8082)
        // Accessible at http://localhost:8082
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
    }
}

