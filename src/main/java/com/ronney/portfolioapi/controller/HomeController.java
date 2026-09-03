package com.ronney.portfolioapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    private final String swaggerUrl;

    public HomeController(
            @Value("${portfolio.api.swagger-url}") String swaggerUrl) {
        this.swaggerUrl = swaggerUrl;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "application", "Portfólio API",
                "status", "Online",
                "swagger", swaggerUrl
        );
    }
}
