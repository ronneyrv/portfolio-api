package com.ronney.portfolioapi.controller;

import com.ronney.portfolioapi.dto.ProjectImageRequestDTO;
import com.ronney.portfolioapi.dto.ProjectImageResponseDTO;
import com.ronney.portfolioapi.service.ProjectImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/images")
@RequiredArgsConstructor
public class ProjectImageController {

    private final ProjectImageService service;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProjectImageResponseDTO> create(
            @PathVariable Integer projectId,
            @Valid @ModelAttribute ProjectImageRequestDTO dto
    ) {
        return ResponseEntity.ok(
                service.create(projectId, dto)
        );
    }

    @PutMapping(
            value = "/{imageId}",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<ProjectImageResponseDTO> update(
            @PathVariable Integer projectId,
            @PathVariable Integer imageId,
            @Valid @ModelAttribute ProjectImageRequestDTO dto
    ) {
        return ResponseEntity.ok(
                service.update(projectId, imageId, dto)
        );
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer projectId,
            @PathVariable Integer imageId
    ) {
        service.delete(projectId, imageId);
        return ResponseEntity.noContent().build();
    }
}