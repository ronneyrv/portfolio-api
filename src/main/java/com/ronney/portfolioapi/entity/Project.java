package com.ronney.portfolioapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, length = 1000)
    private String shortDescription;

    @Column(length = 5000)
    private String description;

    private String coverImageUrl;

    private String githubUrl;

    private String demoUrl;

    @Column(nullable = false)
    private Integer displayOrder;

    private LocalDateTime createdAt;
}
