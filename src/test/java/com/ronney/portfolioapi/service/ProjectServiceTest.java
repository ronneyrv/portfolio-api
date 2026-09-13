package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.dto.ProjectDetailsResponseDTO;
import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.entity.ProjectImage;
import com.ronney.portfolioapi.exception.ResourceNotFoundException;
import com.ronney.portfolioapi.repository.ProjectImageRepository;
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

    @Mock
    private ProjectImageRepository imageRepository;

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

    @Test
    void shouldFindProjectBySlug() {
        when(repository.findBySlug("api-portfolio"))
                .thenReturn(java.util.Optional.of(project));

        ProjectImage firstImage = ProjectImage.builder()
                .id(1)
                .project(project)
                .imageUrl("https://example.com/image-1.jpg")
                .displayOrder(1)
                .altText("First screenshot")
                .build();

        ProjectImage secondImage = ProjectImage.builder()
                .id(2)
                .project(project)
                .imageUrl("https://example.com/image-2.jpg")
                .displayOrder(2)
                .altText("Second screenshot")
                .build();

        when(imageRepository.findByProjectIdOrderByDisplayOrderAsc(1))
                .thenReturn(java.util.List.of(firstImage, secondImage));

        ProjectDetailsResponseDTO response =
                service.findBySlug("api-portfolio");

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Portfolio API", response.getTitle());
        assertEquals("api-portfolio", response.getSlug());
        assertEquals(
                "API REST desenvolvida com Spring Boot para gerenciamento de projetos do portfólio.",
                response.getShortDescription()
        );
        assertEquals(
                "https://example.com/portfolio-api.jpg",
                response.getCoverImageUrl()
        );
        assertNotNull(response.getImages());
        assertEquals(2, response.getImages().size());

        assertEquals(1, response.getImages().get(0).getId());
        assertEquals(1, response.getImages().get(0).getDisplayOrder());
        assertEquals(
                "First screenshot",
                response.getImages().get(0).getAltText()
        );

        assertEquals(2, response.getImages().get(1).getId());
        assertEquals(2, response.getImages().get(1).getDisplayOrder());
        assertEquals(
                "Second screenshot",
                response.getImages().get(1).getAltText()
        );

        verify(imageRepository, times(1))
                .findByProjectIdOrderByDisplayOrderAsc(1);

        verify(repository, times(1))
                .findBySlug("api-portfolio");
    }

    @Test
    void shouldThrowExceptionWhenProjectSlugDoesNotExist() {
        when(repository.findBySlug("non-existent"))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findBySlug("non-existent")
        );

        verify(repository, times(1))
                .findBySlug("non-existent");
    }
}
