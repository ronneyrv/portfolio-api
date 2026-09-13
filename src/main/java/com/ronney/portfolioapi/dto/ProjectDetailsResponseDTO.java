package com.ronney.portfolioapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProjectDetailsResponseDTO {

    private Integer id;
    private String title;
    private String slug;
    private String shortDescription;
    private String description;
    private String coverImageUrl;
    private String githubUrl;
    private String demoUrl;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private List<ProjectImageResponseDTO> images;
}