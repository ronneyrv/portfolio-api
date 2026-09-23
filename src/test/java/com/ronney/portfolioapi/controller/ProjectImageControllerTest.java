package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.dto.ProjectImageResponseDTO;
import com.ronney.portfolioapi.security.CustomUserDetailsService;
import com.ronney.portfolioapi.security.JwtService;
import com.ronney.portfolioapi.service.ProjectImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectImageController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectImageService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldCreateProjectImage() throws Exception {

        ProjectImageResponseDTO response =
                ProjectImageResponseDTO.builder()
                        .id(1)
                        .imageUrl("https://example.com/image.jpg")
                        .displayOrder(1)
                        .altText("Project screenshot")
                        .build();

        when(service.create(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(
                        multipart("/projects/1/images")
                                .file(
                                        "image",
                                        "image-content".getBytes()
                                )
                                .param("displayOrder", "1")
                                .param("altText", "Project screenshot")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.imageUrl")
                                .value("https://example.com/image.jpg")
                )
                .andExpect(
                        jsonPath("$.displayOrder")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.altText")
                                .value("Project screenshot")
                );
    }

    @Test
    void shouldUpdateProjectImage() throws Exception {

        ProjectImageResponseDTO response =
                ProjectImageResponseDTO.builder()
                        .id(1)
                        .imageUrl("https://example.com/updated-image.jpg")
                        .displayOrder(2)
                        .altText("Updated screenshot")
                        .build();

        when(service.update(eq(1), eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(
                        multipart("/projects/1/images/1")
                                .file(
                                        "image",
                                        "updated-image-content".getBytes()
                                )
                                .param("displayOrder", "2")
                                .param("altText", "Updated screenshot")
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                })
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.imageUrl")
                                .value("https://example.com/updated-image.jpg")
                )
                .andExpect(
                        jsonPath("$.displayOrder")
                                .value(2)
                )
                .andExpect(
                        jsonPath("$.altText")
                                .value("Updated screenshot")
                );
    }

    @Test
    void shouldUpdateProjectImageWithoutNewImage() throws Exception {

        ProjectImageResponseDTO response =
                ProjectImageResponseDTO.builder()
                        .id(1)
                        .imageUrl("https://example.com/image.jpg")
                        .displayOrder(2)
                        .altText("Updated screenshot")
                        .build();

        when(service.update(eq(1), eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(
                        multipart("/projects/1/images/1")
                                .param("displayOrder", "2")
                                .param("altText", "Updated screenshot")
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                })
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.imageUrl")
                                .value("https://example.com/image.jpg")
                )
                .andExpect(
                        jsonPath("$.displayOrder")
                                .value(2)
                )
                .andExpect(
                        jsonPath("$.altText")
                                .value("Updated screenshot")
                );
    }

    @Test
    void shouldDeleteProjectImage() throws Exception {

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/projects/1/images/1")
                )
                .andExpect(status().isNoContent());

        org.mockito.Mockito.verify(service)
                .delete(1, 1);
    }
}