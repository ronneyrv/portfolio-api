package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    private Project project;

    @BeforeEach
    void setup() {
        project = Project.builder()
                .id(1)
                .title("Portfolio API")
                .slug("api-portfolio")
                .shortDescription("API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio.")
                .description("Backend project")
                .coverImageUrl("https://example.com/portfolio-api.jpg")
                .githubUrl("https://github.com/ronneyrv/portfolio-api")
                .demoUrl("https://portfolio-api.example.com")
                .displayOrder(20)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCeateProject() {
        ProjectRequestDTO dto = new ProjectRequestDTO();

        dto.setTitle("Portfolio API");
        dto.setSlug("api-portfolio");
        dto.setShortDescription(
                "API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio."
        );
        dto.setDescription("Backend project");
        dto.setCoverImageUrl("https://example.com/portfolio-api.jpg");
        dto.setGithubUrl("https://github.com/ronneyrv/portfolio-api");
        dto.setDemoUrl("https://portfolio-api.example.com");
        dto.setDisplayOrder(20);

        when(repository.save(any(Project.class)))
                .thenReturn(project);

        ProjectResponseDTO response = service.create(dto);

        assertNotNull(response);
        assertEquals("Portfolio API", response.getTitle());
        assertEquals("api-portfolio", response.getSlug());
        assertEquals(
                "API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio.",
                response.getShortDescription()
        );
        assertEquals("Backend project", response.getDescription());
        assertEquals(
                "https://example.com/portfolio-api.jpg",
                response.getCoverImageUrl()
        );
        assertEquals(
                "https://github.com/ronneyrv/portfolio-api",
                response.getGithubUrl()
        );
        assertEquals(
                "https://portfolio-api.example.com",
                response.getDemoUrl()
        );
        assertEquals(20, response.getDisplayOrder());

        verify(repository, times(1))
                .save(any(Project.class));
    }
}
