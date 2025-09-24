package com.springboot_client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final RestClient restClient;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public ClientService(RestClient restClient, OAuth2AuthorizedClientManager authorizedClientManager) {
        this.restClient = restClient;
        this.authorizedClientManager = authorizedClientManager;
    }

    //generates new token
    public List<Map<String, Object>> fetchDataWithNewGeneratingAccessToken() {
        OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("oauth2-keycloak-client")
                .principal("client")
                .build();

        log.info("OAuth2 request: {}", oAuth2AuthorizeRequest);

        OAuth2AuthorizedClient authorizedClient =
                authorizedClientManager.authorize(oAuth2AuthorizeRequest);

        String tokenValue = authorizedClient.getAccessToken().getTokenValue();
        log.info("Token value is : {}", tokenValue);

        return restClient.get()
                .uri("/data")
                .headers(header -> header.setBearerAuth(tokenValue))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });
    }

    //forwards same token
    public List<Map<String, Object>> fetchDataWithOutNewGeneratingAccessToken() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String tokenValue;

        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new IllegalArgumentException("Invalid authentication token");
        }

        tokenValue = jwtAuthenticationToken.getToken().getTokenValue();

        return restClient.get()
                .uri("/data")
                .headers(header -> header.setBearerAuth(tokenValue))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });
    }
}