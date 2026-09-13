package com.ronney.portfolioapi.service;

import com.ronney.portfolioapi.dto.ProjectImageRequestDTO;
import com.ronney.portfolioapi.dto.ProjectImageResponseDTO;
import com.ronney.portfolioapi.entity.Project;
import com.ronney.portfolioapi.entity.ProjectImage;
import com.ronney.portfolioapi.exception.ResourceNotFoundException;
import com.ronney.portfolioapi.repository.ProjectImageRepository;
import com.ronney.portfolioapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectImageService {

    private final ProjectImageRepository imageRepository;
    private final ProjectRepository projectRepository;
    private final FileUploadService fileUploadService;

    public ProjectImageResponseDTO create(
            Integer projectId,
            ProjectImageRequestDTO dto
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Projeto não encontrado"
                        )
                );

        if (dto.getImage() == null || dto.getImage().isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }

        String imageUrl = fileUploadService.uploadFile(dto.getImage());

        ProjectImage image = ProjectImage.builder()
                .project(project)
                .imageUrl(imageUrl)
                .displayOrder(dto.getDisplayOrder())
                .altText(dto.getAltText())
                .build();

        return mapToResponse(imageRepository.save(image));
    }

    public ProjectImageResponseDTO update(
            Integer projectId,
            Integer imageId,
            ProjectImageRequestDTO dto
    ) {
        ProjectImage image = imageRepository.findById(imageId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Imagem não encontrada"
                        )
                );

        if (!image.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Imagem não encontrada"
            );
        }

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = fileUploadService.uploadFile(dto.getImage());
            image.setImageUrl(imageUrl);
        }

        image.setDisplayOrder(dto.getDisplayOrder());
        image.setAltText(dto.getAltText());

        return mapToResponse(imageRepository.save(image));
    }

    public void delete(Integer projectId, Integer imageId) {
        ProjectImage image = imageRepository.findById(imageId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Imagem não encontrada"
                        )
                );

        if (!image.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException(
                    "Imagem não encontrada"
            );
        }

        imageRepository.delete(image);
    }

    private ProjectImageResponseDTO mapToResponse(ProjectImage image) {
        return ProjectImageResponseDTO.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .displayOrder(image.getDisplayOrder())
                .altText(image.getAltText())
                .build();
    }
}