package com.dev.timetracker.repository;

import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RepositoryProject extends JpaRepository<EntityProject, Long> {
    Page<EntityProject> findAllByActiveTrue(Pageable pageable);
    EntityProject findByIdAndActiveTrue(Long id);
    //Set<EntityUser> findUsersById(Long id);
    //void addUsersToProject(Long id, Set<EntityUser> users);
    //void removeUserFromProject(Long id, EntityUser user);
    //Set<EntityTask> findTasksById(Long id);
    //void addTasksToProject(Long id, Set<EntityTask> tasks);
    //void removeTaskFromProject(Long id, EntityTask task);
    //void addTagsToProject(Long id, Set<Tag> tags);
    //void removeTagFromProject(Long id, Tag tag);
}