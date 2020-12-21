package ru.javaops.bootjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(ant("/api/**"))
                .paths(Predicate.not(ant("/api/profile")))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("REST API documentation")
                        .version("1.0")
                        .build())
//                  .securitySchemes(List.of(new BasicAuth("basicAuth")))  // https://github.com/springfox/springfox/issues/3518
                .securitySchemes(List.of(HttpAuthenticationScheme.BASIC_AUTH_BUILDER.name("basicAuth").build()))
                .securityContexts(List.of(SecurityContext.builder()
                        .securityReferences(List.of(new SecurityReference("basicAuth", new AuthorizationScope[0])))
                        .build()
                ));
    }
}
