package com.springboot_client.controller;

import com.springboot_client.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProxyController {

    private final ClientService clientService;

    public ProxyController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/proxy")
    public List<Map<String, Object>> proxy() {
        return clientService.fetchDataWithNewGeneratingAccessToken();
    }

    @GetMapping("/data")
    public List<Map<String, Object>> proxyData() {
        return clientService.fetchDataWithOutNewGeneratingAccessToken();
    }

    @GetMapping("/info")
    public String information() {
        return "Welcome, users";
    }
}
