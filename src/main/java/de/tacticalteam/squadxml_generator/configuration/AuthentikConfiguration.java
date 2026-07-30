package de.tacticalteam.squadxml_generator.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import de.tacticalteam.squadxml_generator.adapter.out.authentik.ApiClient;

@Configuration
@EnableConfigurationProperties(AuthentikProperties.class)
public class AuthentikConfiguration {

    @Bean
    RestClient authentikRestClient(AuthentikProperties properties) {
        return RestClient.builder()
            .baseUrl(properties.baseUrl())
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.bearerToken())
            .build();
    }

    @Bean
    @Primary
    ApiClient authentikApiClient(AuthentikProperties properties) {
        var restClient = RestClient.builder()
            .build();
        var apiClient = new ApiClient(restClient);
        apiClient.setBasePath(properties.baseUrl());
        apiClient.setBearerToken(properties.bearerToken());
        return apiClient;
    }
}