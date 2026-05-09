package com.ronney.portfolioapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRequestDTO {
    @NotBlank(message = "Título obrigatorio")
    private String title;

    private String description;

    private String imageURL;

    private String githubUrl;

    private String demoUrl;
}
