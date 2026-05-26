package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.dto.ProjectRequestDTO;
import com.ronney.portfolioapi.dto.ProjectResponseDTO;
import com.ronney.portfolioapi.service.FileUploadService;
import com.ronney.portfolioapi.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(value="/{id}", consumes="multipart/form-data")
    public ResponseEntity<ProjectResponseDTO> update(
            @PathVariable Integer id,
            @ModelAttribute ProjectRequestDTO dto
    ) {
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = fileUploadService.uploadFile(dto.getImage());
            dto.setImageUrl(imageUrl);
        }
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
