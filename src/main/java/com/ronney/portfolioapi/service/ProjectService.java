package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.exception.ResourceNotFoundException;
import com.ronney.portfolioapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public Page<ProjectResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
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
                .imageUrl(dto.getImageURL())
                .githubUrl(dto.getGithubUrl())
                .demoUrl(dto.getDemoUrl())
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
                .imageUrl(project.getImageUrl())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .createdAt(project.getCreatedAt())
                .build();
    }

    public Project update(Integer id, Project project) {
        Project existingProject = findEntityById(id);

        existingProject.setTitle(project.getTitle());
        existingProject.setDescription(project.getDescription());
        existingProject.setImageUrl(project.getImageUrl());
        existingProject.setGithubUrl(project.getGithubUrl());
        existingProject.setDemoUrl(project.getDemoUrl());

        return repository.save(existingProject);
    }

    public void delete(Integer id) {
        Project project = findEntityById(id);

        repository.delete(project);
    }
}