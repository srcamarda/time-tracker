package com.dev.timetracker.repository;

import com.dev.timetracker.entity.EntityTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTask extends JpaRepository<EntityTask, Long> {
    Page<EntityTask> findAllByActiveTrue(Pageable pageable);
    EntityTask findByIdAndActiveTrue(Long id);
    //Page<EntityTask> findAllById_userAndActiveTrue(Long id_user, Pageable pageable);
}