package com.ronney.portfolioapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectImageResponseDTO {

    private Integer id;
    private String imageUrl;
    private Integer displayOrder;
    private String altText;
}