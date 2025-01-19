package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@ComponentScan
@Configuration
public class ConfigurationClient {
    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
