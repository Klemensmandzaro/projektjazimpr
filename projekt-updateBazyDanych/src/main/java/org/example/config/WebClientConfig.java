package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://us.api.blizzard.com")
                .defaultHeader("Authorization", "Bearer EU5pEZlaIZHbJW4XciuIR582B8OcsKiajO")
                .build();
    }
}

