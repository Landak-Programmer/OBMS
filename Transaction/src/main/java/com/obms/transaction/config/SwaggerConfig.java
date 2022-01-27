package com.obms.transaction.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Value("${application.swagger.serverUrl}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI(@Value("${application.swagger.description}") String appDescription,
                                 @Value("${application.swagger.version}") String appVersion) {
        final Server server = new Server();

        return new OpenAPI()
                .servers(Collections.singletonList(server))
                .info(new Info()
                        .title("OBMS Account REST API")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

}
