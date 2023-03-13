package com.ffhanyang.kiosk.configure;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigure {
    private final JwtTokenConfigure jwtTokenConfigure;

    public SwaggerConfigure(JwtTokenConfigure jwtTokenConfigure) {
        this.jwtTokenConfigure = jwtTokenConfigure;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("kiosk-Server")
                .contact(new Contact().name("GyeongHoKim").email("rlarudgh2017@gmail.com"))
                .version("1.0.0"))
            .components(new Components()
                .addSecuritySchemes("apiKey", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)
                    .name(jwtTokenConfigure.getHeader())))
            .addSecurityItem(new SecurityRequirement().addList("apiKey", "global"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("public-api")
            .pathsToMatch("/public/**")
            .build();
    }

    @Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
            .group("private-api")
            .pathsToMatch("/api/**")
            .build();
    }

}