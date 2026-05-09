package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.service.FileUploadService;
import com.ronney.portfolioapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;
    private final FileUploadService fileUploadService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProjectResponseDTO> create(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false)MultipartFile image,
            @RequestParam String githubUrl,
            @RequestParam String demoUrl
    ) {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            imageUrl = fileUploadService.uploadFile(image);
        }

        ProjectRequestDTO dto = new ProjectRequestDTO();

        dto.setTitle(title);
        dto.setDescription(description);
        dto.setImageURL(imageUrl);
        dto.setGithubUrl(githubUrl);
        dto.setDemoUrl(demoUrl);

        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Integer id, @RequestBody Project project) {
        return ResponseEntity.ok(service.update(id, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
