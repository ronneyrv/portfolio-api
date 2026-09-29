package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.dto.ProjectDetailsResponseDTO;
import com.ronney.portfolioapi.dto.ProjectImageResponseDTO;
import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.entity.ProjectImage;
import com.ronney.portfolioapi.exception.ResourceNotFoundException;
import com.ronney.portfolioapi.repository.ProjectImageRepository;
import com.ronney.portfolioapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final ProjectImageRepository imageRepository;

    public Page<ProjectResponseDTO> findAll(Pageable pageable) {
        Pageable orderedPageable =
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("displayOrder")
                );

        return repository.findAll(orderedPageable)
                .map(this::mapToResponse);
    }

    public Page<ProjectResponseDTO> searchByTitle(
            String title,
            Pageable pageable
    ) {
        return repository.findByTitleContainingIgnoreCase(title, pageable)
                .map(this::mapToResponse);
    }

    public ProjectDetailsResponseDTO findById(Integer id) {
        Project project = findEntityById(id);

        return ProjectDetailsResponseDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .shortDescription(project.getShortDescription())
                .description(project.getDescription())
                .coverImageUrl(project.getCoverImageUrl())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .displayOrder(project.getDisplayOrder())
                .createdAt(project.getCreatedAt())
                .images(
                        imageRepository
                                .findByProjectIdOrderByDisplayOrderAsc(
                                        project.getId()
                                )
                                .stream()
                                .map(this::mapToImageResponse)
                                .toList()
                )
                .build();
    }

    private Project findEntityById(Integer id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Projeto não encontrado"
                        )
                );
    }

    public ProjectResponseDTO create(ProjectRequestDTO dto) {
        Project project = Project.builder()
                .title(dto.getTitle())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .description(dto.getDescription())
                .coverImageUrl(dto.getCoverImageUrl())
                .githubUrl(dto.getGithubUrl())
                .demoUrl(dto.getDemoUrl())
                .displayOrder(dto.getDisplayOrder())
                .createdAt(LocalDateTime.now())
                .build();

        Project savedProject = repository.save(project);

        return mapToResponse(savedProject);
    }

    private ProjectResponseDTO mapToResponse(Project project) {
        return ProjectResponseDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .shortDescription(project.getShortDescription())
                .description(project.getDescription())
                .coverImageUrl(project.getCoverImageUrl())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .displayOrder(project.getDisplayOrder())
                .createdAt(project.getCreatedAt())
                .build();
    }

    public ProjectResponseDTO update(
            Integer id,
            ProjectRequestDTO dto
    ) {
        Project existingProject = findEntityById(id);

        existingProject.setTitle(dto.getTitle());
        existingProject.setSlug(dto.getSlug());
        existingProject.setShortDescription(dto.getShortDescription());
        existingProject.setDescription(dto.getDescription());
        existingProject.setGithubUrl(dto.getGithubUrl());
        existingProject.setDemoUrl(dto.getDemoUrl());
        existingProject.setDisplayOrder(dto.getDisplayOrder());

        if (dto.getCoverImageUrl() != null) {
            existingProject.setCoverImageUrl(dto.getCoverImageUrl());
        }

        Project updatedProject = repository.save(existingProject);

        return mapToResponse(updatedProject);
    }

    public void delete(Integer id) {
        Project project = findEntityById(id);
        repository.delete(project);
    }

    private ProjectImageResponseDTO mapToImageResponse(
            ProjectImage image
    ) {
        return ProjectImageResponseDTO.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .displayOrder(image.getDisplayOrder())
                .altText(image.getAltText())
                .build();
    }

    public ProjectDetailsResponseDTO findBySlug(String slug) {
        Project project = repository.findBySlug(slug)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Projeto não encontrado"
                        )
                );

        return ProjectDetailsResponseDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .shortDescription(project.getShortDescription())
                .description(project.getDescription())
                .coverImageUrl(project.getCoverImageUrl())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .displayOrder(project.getDisplayOrder())
                .createdAt(project.getCreatedAt())
                .images(
                        imageRepository
                                .findByProjectIdOrderByDisplayOrderAsc(
                                        project.getId()
                                )
                                .stream()
                                .map(this::mapToImageResponse)
                                .toList()
                )
                .build();
    }
}
