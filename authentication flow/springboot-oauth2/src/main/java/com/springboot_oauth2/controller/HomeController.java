package com.springboot_oauth2.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome hello";
    }

    @GetMapping("/token")
    public Map<String, Object> getToken(OAuth2AuthenticationToken authenticationToken) {
        Object email = authenticationToken.getPrincipal().getAttribute("email");
        Object name = authenticationToken.getPrincipal().getAttribute("name");
        String roles = authenticationToken.getAuthorities().toString();
        return Map.of(
                "user email", email,
                "firstname lastname", name,
                "roles", roles
        );
    }
}
