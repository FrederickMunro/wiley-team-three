package wileyt3.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Team 3 Stock Portfolio API")
                        .description("The API provides different users with the capabilities to track and manage their investments effectively." +
                                "It allows including adding, viewing, updating, and deleting stocks in a user's portfolio." +
                                "It also offers features for viewing detailed stock information and managing user profiles."
                        )
                        .version("v1.0.0"));
    }
}
