package com.densoft.portfolio.restClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestConfig {

    @Value("${devto.token}")
    private String token;

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().baseUrl("https://dev.to/api").defaultHeaders(this::addHttpHeaders).build();
    }

    public void addHttpHeaders(HttpHeaders httpHeaders) {
        httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.add("api-key", token);
    }
}
