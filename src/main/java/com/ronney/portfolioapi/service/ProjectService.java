package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.exception.ResourceNotFoundException;
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
    public Page<ProjectResponseDTO> searchByTitle(String title, Pageable pageable) {
        return repository.findByTitleContainingIgnoreCase(title, pageable)
                .map(this::mapToResponse);
    }

    public ProjectResponseDTO findById(Integer id) {
        Project project = findEntityById(id);
        return mapToResponse(project);
    }

    private Project findEntityById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
    }

    public ProjectResponseDTO create(ProjectRequestDTO dto) {
        Project project = Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .coverImageUrl(dto.getImageUrl())
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
                .description(project.getDescription())
                .imageUrl(project.getCoverImageUrl())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .displayOrder(project.getDisplayOrder())
                .createdAt(project.getCreatedAt())
                .build();
    }

    public ProjectResponseDTO update(Integer id, ProjectRequestDTO dto) {
        Project existingProject = findEntityById(id);

        existingProject.setTitle(dto.getTitle());
        existingProject.setDescription(dto.getDescription());
        existingProject.setGithubUrl(dto.getGithubUrl());
        existingProject.setDemoUrl(dto.getDemoUrl());
        existingProject.setDisplayOrder(dto.getDisplayOrder());

        if (dto.getImageUrl() != null) {
            existingProject.setCoverImageUrl(dto.getImageUrl());
        }

        Project updatedProject = repository.save(existingProject);

        return mapToResponse(updatedProject);
    }

    public void delete(Integer id) {
        Project project = findEntityById(id);

        repository.delete(project);
    }
}