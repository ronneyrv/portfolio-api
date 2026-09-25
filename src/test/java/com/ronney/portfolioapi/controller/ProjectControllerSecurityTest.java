package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.security.CustomUserDetailsService;
import com.ronney.portfolioapi.security.JwtService;
import com.ronney.portfolioapi.service.FileUploadService;
import com.ronney.portfolioapi.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc
class ProjectControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private FileUploadService fileUploadService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldRejectUnauthenticatedProjectCreation() throws Exception {
        mockMvc.perform(
                post("/projects")
                        .contentType("multipart/form-data")
        ).andExpect(status().isForbidden());
    }
}