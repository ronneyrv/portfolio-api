package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Project findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
    }

    public Project create(Project project) {
        project.setCreatedAt(LocalDateTime.now());

        return repository.save(project);
    }

    public Project update(Integer id, Project project) {
        Project existingProject = findById(id);

        existingProject.setTitle(project.getTitle());
        existingProject.setDescription(project.getDescription());
        existingProject.setImageUrl(project.getImageUrl());
        existingProject.setGithubUrl(project.getGithubUrl());
        existingProject.setDemoUrl(project.getDemoUrl());

        return repository.save(existingProject);
    }

    public void delete(Integer id) {
        Project project = findById(id);

        repository.delete(project);
    }
}