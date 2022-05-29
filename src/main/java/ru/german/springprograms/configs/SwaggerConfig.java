package ru.german.springprograms.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Комплекс \"Построитель тьюторов\". Сервис Поддержки").description("REST-сервисы с описанием параметров"));
    }
}