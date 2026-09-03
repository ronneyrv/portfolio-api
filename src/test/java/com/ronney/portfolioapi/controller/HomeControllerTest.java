package com.ronney.portfolioapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@TestPropertySource(properties = {
        "portfolio.api.swagger-url=https://portfolio-api.ronneyrocha.com.br/swagger-ui/index.html"
})
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnApiInformation() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application").value("Portfólio API"))
                .andExpect(jsonPath("$.status").value("Online"))
                .andExpect(jsonPath("$.swagger")
                        .value("https://portfolio-api.ronneyrocha.com.br/swagger-ui/index.html"))
                .andExpect(jsonPath("$.ststus").doesNotExist());
    }
}
