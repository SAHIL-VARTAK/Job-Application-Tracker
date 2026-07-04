package com.jobtracker.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI jobTrackerOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title(
                        "Job Application Tracker API"
                    )
                    .version("1.0.0")
                    .description(
                        """
                        REST API for managing job applications,
                        interview stages, and recruitment progress.
                        """
                    )
                    .contact(
                        new Contact()
                            .name("Sahil Vartak")
                            .url(
                                "https://github.com/SAHIL-VARTAK"
                            )
                    )
            )
            .servers(
                List.of(
                    new Server()
                        .url("http://localhost:8080")
                        .description(
                                "Local Development Server"
                        )
                )
            );
    }
}