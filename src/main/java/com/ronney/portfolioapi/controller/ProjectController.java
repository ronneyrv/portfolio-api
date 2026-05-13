package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.service.FileUploadService;
import com.ronney.portfolioapi.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @Operation(summary = "List all projects")
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
            @Valid @ModelAttribute ProjectRequestDTO dto
    ) {
        String imageUrl = null;

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            imageUrl = fileUploadService.uploadFile(dto.getImage());
        }

        dto.setImageUrl(imageUrl);

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
