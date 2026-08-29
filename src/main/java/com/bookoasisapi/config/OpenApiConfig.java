package com.bookoasisapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Book Oasis API",
                version = "1.0",
                description = "REST API for managing Mr Dewey's bookshop stock"
        )
)
@Configuration
public class OpenApiConfig {
}
