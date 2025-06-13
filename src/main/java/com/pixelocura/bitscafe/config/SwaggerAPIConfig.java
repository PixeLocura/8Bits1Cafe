package com.pixelocura.bitscafe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerAPIConfig {
    @Value("${bitscafe.openapi.dev-url}")
    private String devUrl;

    @Value("${bitscafe.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development Server");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Production Server");

        // Informacion de contacto
        Contact contact = new Contact();
        contact.setName("PixeLocura");
        contact.setEmail("pixelocura@gmail.com");
        contact.setUrl("https://github.com/PixeLocura/");

        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        // // Informacion general de la API
        Info info = new Info()
            .title("8Bits1Cafe API")
            .version("1.0.0")
            .contact(contact)
            .description("API para la aplicación 8Bits1Cafe.")
            .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .addServersItem(devServer)
                .addServersItem(prodServer);
    }
}
