package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.dto.ProjectImageRequestDTO;
import com.ronney.portfolioapi.dto.ProjectImageResponseDTO;
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
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectImageServiceTest {

    @Mock
    private ProjectImageRepository imageRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private FileUploadService fileUploadService;

    @InjectMocks
    private ProjectImageService service;

    private Project project;
    private ProjectImage image;

    @BeforeEach
    void setup() {
        project = Project.builder()
                .id(1)
                .title("Portfolio API")
                .build();

        image = ProjectImage.builder()
                .id(1)
                .project(project)
                .imageUrl("https://example.com/image.jpg")
                .displayOrder(1)
                .altText("Project screenshot")
                .build();
    }

    @Test
    void shouldCreateProjectImage() {

        ProjectImageRequestDTO dto = new ProjectImageRequestDTO();

        MockMultipartFile file =
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "image-content".getBytes()
                );

        dto.setImage(file);
        dto.setDisplayOrder(1);
        dto.setAltText("Project screenshot");

        when(projectRepository.findById(1))
                .thenReturn(Optional.of(project));

        when(fileUploadService.uploadFile(file))
                .thenReturn("https://example.com/image.jpg");

        when(imageRepository.save(any(ProjectImage.class)))
                .thenReturn(image);

        ProjectImageResponseDTO response =
                service.create(1, dto);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(
                "https://example.com/image.jpg",
                response.getImageUrl()
        );
        assertEquals(1, response.getDisplayOrder());
        assertEquals(
                "Project screenshot",
                response.getAltText()
        );

        verify(projectRepository).findById(1);
        verify(fileUploadService).uploadFile(file);
        verify(imageRepository).save(any(ProjectImage.class));
    }

    @Test
    void shouldThrowExceptionWhenProjectDoesNotExist() {

        ProjectImageRequestDTO dto = new ProjectImageRequestDTO();

        when(projectRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.create(1, dto)
        );

        verify(projectRepository).findById(1);
        verifyNoInteractions(fileUploadService, imageRepository);
    }

    @Test
    void shouldUpdateProjectImage() {

        ProjectImageRequestDTO dto = new ProjectImageRequestDTO();

        MockMultipartFile file =
                new MockMultipartFile(
                        "image",
                        "updated-image.jpg",
                        "image/jpeg",
                        "updated-image-content".getBytes()
                );

        dto.setImage(file);
        dto.setDisplayOrder(2);
        dto.setAltText("Updated screenshot");

        when(imageRepository.findById(1))
                .thenReturn(Optional.of(image));

        when(fileUploadService.uploadFile(file))
                .thenReturn("https://example.com/updated-image.jpg");

        when(imageRepository.save(image))
                .thenReturn(image);

        ProjectImageResponseDTO response =
                service.update(1, 1, dto);

        assertNotNull(response);
        assertEquals(
                "https://example.com/updated-image.jpg",
                response.getImageUrl()
        );
        assertEquals(2, response.getDisplayOrder());
        assertEquals(
                "Updated screenshot",
                response.getAltText()
        );

        verify(imageRepository).findById(1);
        verify(fileUploadService).uploadFile(file);
        verify(imageRepository).save(image);
    }

    @Test
    void shouldThrowExceptionWhenImageDoesNotBelongToProject() {

        Project otherProject = Project.builder()
                .id(2)
                .title("Other Project")
                .build();

        image.setProject(otherProject);

        ProjectImageRequestDTO dto = new ProjectImageRequestDTO();
        dto.setDisplayOrder(2);

        when(imageRepository.findById(1))
                .thenReturn(Optional.of(image));

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.update(1, 1, dto)
        );

        verify(imageRepository).findById(1);
        verifyNoMoreInteractions(imageRepository);
        verifyNoInteractions(fileUploadService);
    }

    @Test
    void shouldDeleteProjectImage() {

        when(imageRepository.findById(1))
                .thenReturn(Optional.of(image));

        service.delete(1, 1);

        verify(imageRepository).findById(1);
        verify(imageRepository).delete(image);
    }

    @Test
    void shouldThrowExceptionWhenImageIsMissing() {

        ProjectImageRequestDTO dto = new ProjectImageRequestDTO();
        dto.setDisplayOrder(1);

        when(projectRepository.findById(1))
                .thenReturn(Optional.of(project));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.create(1, dto)
        );

        verify(projectRepository).findById(1);
        verifyNoInteractions(fileUploadService, imageRepository);
    }
}