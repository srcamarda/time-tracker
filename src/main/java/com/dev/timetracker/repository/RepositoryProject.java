package com.dev.timetracker.repository;

import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProject extends JpaRepository<EntityProject, Long> {
    Page<EntityProject> findAllByActiveTrue(Pageable pageable);
    EntityProject findByIdAndActiveTrue(Long id);

//    TODO: Make the join queries work
//    Page<EntityUser> findUsersById(Long id, Pageable pageable);
//    Page<EntityTask> findTasksById(Long id, Pageable pageable);
}