package com.ronney.portfolioapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectImageRequestDTO {

    private MultipartFile image;

    @Size(max = 500)
    private String altText;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;
}