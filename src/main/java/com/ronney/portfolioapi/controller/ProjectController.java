package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.service.FileUploadService;
import com.ronney.portfolioapi.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;
    private final FileUploadService fileUploadService;

    @Operation(summary = "List all projects with pagination")
    @GetMapping
    public ResponseEntity<Page<ProjectResponseDTO>> findAll(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt"
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProjectResponseDTO>> search(
            @RequestParam String title,
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt"
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                service.searchByTitle(title, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> findById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Create a new project")
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
