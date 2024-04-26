package com.dev.timetracker.repository;

import com.dev.timetracker.entity.EntityProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProject extends JpaRepository<EntityProject, Long> {
    Page<EntityProject> findAllByActiveTrue(Pageable pageable);
    EntityProject findByIdAndActiveTrue(Long id);
}