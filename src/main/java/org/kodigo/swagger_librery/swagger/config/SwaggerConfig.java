package org.kodigo.swagger_librery.swagger.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Biblioteca API")
                        .description("Api rest para gestion de biblioteca con libors y autores")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kodigo org")
                                .email("kodigo@kodigo.org")
                                .url("https://kodigo.org/"))
                        .license(new License()
                                .name("MIT Licence")
                                .url("https://opensource.org/license/mit")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.kodibiblioteca.com")
                                .description("Servidor de producccion")

                ));
    }
}
