package de.tacticalteam.squadxml_generator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "authentik")
public record AuthentikProperties(String baseUrl, String bearerToken) {
}