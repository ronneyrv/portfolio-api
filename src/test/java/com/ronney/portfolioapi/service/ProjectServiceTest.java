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
                .description("Backend project")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCeateProject() {
        ProjectRequestDTO dto = new ProjectRequestDTO();

        dto.setTitle("Portfolio API");
        dto.setDescription("Backend project");

        when(repository.save(any(Project.class)))
                .thenReturn(project);

        ProjectResponseDTO response = service.create(dto);

        assertNotNull(response);
        assertEquals("Portfolio API", response.getTitle());

        verify(repository, times(1))
                .save(any(Project.class));
    }
}
