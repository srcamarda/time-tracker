package com.dev.timetracker.repository;

import com.dev.timetracker.entity.EntityProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryProject extends JpaRepository<EntityProject, Long> {
    Page<EntityProject> findAllByActiveTrue(Pageable pageable);
    EntityProject findByIdAndActiveTrue(Long id);
    List<EntityProject> findByUsersId(Long userId);
}