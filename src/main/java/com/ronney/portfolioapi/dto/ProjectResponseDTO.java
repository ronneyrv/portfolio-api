package com.ronney.portfolioapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponseDTO {
    private Integer id;

    private String title;

    private String description;

    private String imageUrl;

    private String githubUrl;

    private String demoUrl;

    private LocalDateTime createdAt;
}
