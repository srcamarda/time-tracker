package com.dev.timetracker.repository;

import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTask extends JpaRepository<EntityTask, Long> {
    Page<EntityTask> findAllByActiveTrue(Pageable pageable);
    EntityTask findByIdAndActiveTrue(Long id);
    Page<EntityTask> findAllByIdUserAndActiveTrue(EntityUser idUser, Pageable pageable);
}