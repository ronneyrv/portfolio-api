package com.ronney.portfolioapi.repository;

import com.ronney.portfolioapi.entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Integer> {

    List<ProjectImage> findByProjectIdOrderByDisplayOrderAsc(Integer projectId);
}