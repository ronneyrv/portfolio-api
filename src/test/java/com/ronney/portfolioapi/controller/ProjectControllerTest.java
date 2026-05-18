package com.ronney.portfolioapi.controller;


import com.ronney.portfolioapi.dto.ProjectResponseDTO;
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
                        .description("Backend")
                        .createdAt(LocalDateTime.now())
                        .build();

        Page<ProjectResponseDTO> page =
                new PageImpl<>(
                        new ArrayList<>() {{
                            add(dto);
                        }},
                        PageRequest.of(0,10),
                        1
                );

        when(service.findAll(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.content[0].title")
                                .value("Portfolio API")
                );
    }
}