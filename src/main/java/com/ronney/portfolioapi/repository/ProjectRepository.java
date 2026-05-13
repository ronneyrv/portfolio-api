package com.ronney.portfolioapi.repository;

import com.ronney.portfolioapi.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Page<Project> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );
}
