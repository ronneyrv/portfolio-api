package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.dto.ProjectDetailsResponseDTO;
import com.ronney.portfolioapi.dto.ProjectImageResponseDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.exception.ResourceNotFoundException;
import com.ronney.portfolioapi.service.FileUploadService;
import com.ronney.portfolioapi.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService service;

    @MockitoBean
    private FileUploadService fileUploadService;

    @Test
    void shouldReturnProjects() throws Exception {

        ProjectResponseDTO dto =
                ProjectResponseDTO.builder()
                        .id(1)
                        .title("Portfolio API")
                        .slug("api-portfolio")
                        .shortDescription(
                                "API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio."
                        )
                        .description("Backend")
                        .coverImageUrl("https://example.com/portfolio-api.jpg")
                        .githubUrl("https://github.com/ronneyrv/portfolio-api")
                        .demoUrl("https://portfolio-api.example.com")
                        .displayOrder(20)
                        .createdAt(LocalDateTime.now())
                        .build();

        Page<ProjectResponseDTO> page =
                new PageImpl<>(
                        new ArrayList<>() {{
                            add(dto);
                        }},
                        PageRequest.of(0, 10),
                        1
                );

        when(service.findAll(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.content[0].title")
                                .value("Portfolio API")
                )
                .andExpect(
                        jsonPath("$.content[0].slug")
                                .value("api-portfolio")
                )
                .andExpect(
                        jsonPath("$.content[0].shortDescription")
                                .value(
                                        "API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio."
                                )
                )
                .andExpect(
                        jsonPath("$.content[0].coverImageUrl")
                                .value("https://example.com/portfolio-api.jpg")
                )
                .andExpect(
                        jsonPath("$.content[0].githubUrl")
                                .value("https://github.com/ronneyrv/portfolio-api")
                )
                .andExpect(
                        jsonPath("$.content[0].demoUrl")
                                .value("https://portfolio-api.example.com")
                )
                .andExpect(
                        jsonPath("$.content[0].displayOrder")
                                .value(20)
                );
    }

    @Test
    void shouldReturnProjectBySlug() throws Exception {

        ProjectDetailsResponseDTO dto =
                ProjectDetailsResponseDTO.builder()
                        .id(1)
                        .title("Portfolio API")
                        .slug("api-portfolio")
                        .shortDescription(
                                "API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio."
                        )
                        .description("Backend")
                        .coverImageUrl("https://example.com/portfolio-api.jpg")
                        .githubUrl("https://github.com/ronneyrv/portfolio-api")
                        .demoUrl("https://portfolio-api.example.com")
                        .displayOrder(20)
                        .createdAt(LocalDateTime.now())
                        .images(
                                List.of(
                                        ProjectImageResponseDTO.builder()
                                                .id(1)
                                                .imageUrl("https://example.com/image-1.jpg")
                                                .displayOrder(1)
                                                .altText("Project screenshot")
                                                .build(),
                                        ProjectImageResponseDTO.builder()
                                                .id(2)
                                                .imageUrl("https://example.com/image-2.jpg")
                                                .displayOrder(2)
                                                .altText("Another project screenshot")
                                                .build()
                                )
                        )
                        .build();

        when(service.findBySlug("api-portfolio"))
                .thenReturn(dto);

        mockMvc.perform(get("/projects/api-portfolio"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.title")
                                .value("Portfolio API")
                )
                .andExpect(
                        jsonPath("$.slug")
                                .value("api-portfolio")
                )
                .andExpect(
                        jsonPath("$.shortDescription")
                                .value(
                                        "API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio."
                                )
                )
                .andExpect(
                        jsonPath("$.coverImageUrl")
                                .value("https://example.com/portfolio-api.jpg")
                )
                .andExpect(
                        jsonPath("$.githubUrl")
                                .value("https://github.com/ronneyrv/portfolio-api")
                )
                .andExpect(
                        jsonPath("$.demoUrl")
                                .value("https://portfolio-api.example.com")
                )
                .andExpect(
                        jsonPath("$.displayOrder")
                                .value(20)
                )
                .andExpect(
                        jsonPath("$.images[0].id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.images[0].displayOrder")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.images[0].altText")
                                .value("Project screenshot")
                )
                .andExpect(
                        jsonPath("$.images[1].id")
                                .value(2)
                )
                .andExpect(
                        jsonPath("$.images[1].displayOrder")
                                .value(2)
                );
    }

    @Test
    void shouldReturnNotFoundWhenProjectSlugDoesNotExist() throws Exception {

        when(service.findBySlug("non-existent"))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Projeto não encontrado"
                        )
                );

        mockMvc.perform(get("/projects/non-existent"))
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.message")
                                .value("Projeto não encontrado")
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(404)
                );
    }
}