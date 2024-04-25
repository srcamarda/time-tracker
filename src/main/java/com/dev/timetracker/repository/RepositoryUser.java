package com.dev.timetracker.repository;

import com.dev.timetracker.entity.EntityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUser extends JpaRepository<EntityUser, Long> {
    Page<EntityUser> findAllByActiveTrue(Pageable pageable);
    EntityUser findByIdAndActiveTrue(Long id);
}