package com.ronney.portfolioapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Slug is required")
    @Size(max = 255)
    private String slug;

    @NotBlank(message = "Short description is required")
    @Size(max = 1000)
    private String shortDescription;

    @Size(max = 5000)
    private String description;

    private String coverImageUrl;

    private MultipartFile image;

    private String githubUrl;

    private String demoUrl;

    private Integer displayOrder;
}
