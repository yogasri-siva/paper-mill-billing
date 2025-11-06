package com.example.papermill.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi billingApi() {
        return GroupedOpenApi.builder()
                .group("papermill")
                .packagesToScan("com.example.papermill.controller")
                .addOpenApiCustomizer(openApi -> openApi.setInfo(new Info()
                        .title("Paper Mill Billing API")
                        .version("1.0.0")
                        .description("REST endpoints for creating and managing paper orders")))
                .build();
    }
}
