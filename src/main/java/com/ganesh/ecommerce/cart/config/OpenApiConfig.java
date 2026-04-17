package com.ganesh.ecommerce.cart.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shoppingCartOpenAPI() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local development server");

        Contact contact = new Contact()
                .name("Ganesh")
                .email("ganesh@example.com");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("Shopping Cart API")
                .version("1.0.0")
                .description("REST API documentation for users, products, addresses, carts, and cart items.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer))
                .externalDocs(new ExternalDocumentation()
                        .description("OpenAPI specification")
                        .url("https://spec.openapis.org/oas/latest.html"));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cartApi() {
        return GroupedOpenApi.builder()
                .group("cart")
                .pathsToMatch("/user/usercart/**", "/user/usercartitem/**")
                .build();
    }
}
