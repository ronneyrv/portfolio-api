package com.ronney.portfolioapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "application","Portfólio API",
                "ststus", "Online",
                "swagger","/swagger-ui/index.html"
        );
    }
}
