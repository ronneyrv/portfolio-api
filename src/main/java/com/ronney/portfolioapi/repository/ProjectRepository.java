package com.ronney.portfolioapi.repository;

import com.ronney.portfolioapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
