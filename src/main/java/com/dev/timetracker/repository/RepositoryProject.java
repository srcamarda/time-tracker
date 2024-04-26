package com.dev.timetracker.repository;

import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RepositoryProject extends JpaRepository<EntityProject, Long> {
    Page<EntityProject> findAllByActiveTrue(Pageable pageable);
    EntityProject findByIdAndActiveTrue(Long id);

//    TODO: Make the join queries work
//    Page<EntityUser> findUsersById(Long id, Pageable pageable);
//    Page<EntityTask> findTasksById(Long id, Pageable pageable);

//    @Modifying
//    @Query(value = "insert into project_users (id_project, id_user) values (?1, ?2)", nativeQuery = true)
//    void addUserToProject(Long id, EntityUser user);

//    @Modifying
//    @Query(value = "insert into project_tasks (id_project, id_task) values (?1, ?2)", nativeQuery = true)
//    void addTaskToProject(Long id, EntityTask task);

//    void addTagToProject(Long id, Tag tag);

//    void removeUserFromProject(Long id, EntityUser user);
//    void removeTaskFromProject(Long id, EntityTask task);
//    void removeTagFromProject(Long id, Tag tag);
}