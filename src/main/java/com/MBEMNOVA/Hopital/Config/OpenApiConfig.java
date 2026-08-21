package com.MBEMNOVA.Hopital.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hopitalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestion Hôpital")
                        .description("API de gestion des rendez-vous médicaux : hôpitaux, patients, rendez-vous, "
                                + "avec gestion de la capacité et des conflits d'horaire.")
                        .version("1.0.0"));
    }
}