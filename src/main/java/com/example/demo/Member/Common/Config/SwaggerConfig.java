package com.example.demo.Member.Common.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(/*@Value("${springdoc.}") String springdocVersion*/) { //TODO @value
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("spring-base-project")
                        .version("v1.0.0"/*springdocVersion*/)
                        .description("설-명"));
    }
}
