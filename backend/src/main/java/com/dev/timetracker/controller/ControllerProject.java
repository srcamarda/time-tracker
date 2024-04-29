package com.dev.timetracker.controller;

import com.dev.timetracker.dto.project.DTOCreateProject;
import com.dev.timetracker.dto.project.DTOListProject;
import com.dev.timetracker.dto.project.DTOUpdateProject;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.dto.user.DTOUpdateUser;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.utility.category.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("projects")
public class ControllerProject {

    @Autowired
    private RepositoryProject repositoryProject;
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryTask repositoryTask;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid DTOCreateProject data) {
        repositoryProject.save(new EntityProject(data));
    }

    @GetMapping({"{id}"})
    public DTOListProject get(@PathVariable Long id) {
        return new DTOListProject(repositoryProject.findByIdAndActiveTrue(id));
    }

    @GetMapping
    public List<DTOListProject> list(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return repositoryProject.findAllByActiveTrue(pageable).map(DTOListProject::new).getContent();
    }

    @GetMapping("all")
    public List<DTOListProject> listAll(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return repositoryProject.findAllByActiveTrue(pageable).map(DTOListProject::new).getContent();
    }

    @GetMapping("{id}/users")
    public List<DTOListUser> listUsers(@PathVariable Long id, @PageableDefault(sort = {"id"}) Pageable pageable) {
        return repositoryProject.getReferenceById(id).getUsers().stream().map(DTOListUser::new).toList();
    }

    @GetMapping("{id}/tasks")
    public List<DTOListTask> listTasks(@PathVariable Long id, @PageableDefault(sort = {"id"}) Pageable pageable) {
        return repositoryProject.getReferenceById(id).getTasks().stream().map(DTOListTask::new).toList();
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateProject data) {
        var project = repositoryProject.getReferenceById(data.id());
        project.update(data);
    }

    @PutMapping("{id}/users")
    @Transactional
    public void addUserToProject(@PathVariable Long id, @RequestBody DTOUpdateUser user) {
        EntityUser entityUser = repositoryUser.getReferenceById(user.id());
        EntityProject project = repositoryProject.getReferenceById(id);
        Set<EntityUser> users = project.getUsers();
        users.add(entityUser);
        project.setUsers(users);
        repositoryProject.save(project);
    }

    @PutMapping("{id}/tasks")
    @Transactional
    public void addTaskToProject(@PathVariable Long id, @RequestBody DTOUpdateTask task) {
        EntityTask entityTask = repositoryTask.getReferenceById(task.id());
        EntityProject project = repositoryProject.getReferenceById(id);
        Set<EntityTask> tasks = project.getTasks();
        tasks.add(entityTask);
        project.setTasks(tasks);
        repositoryProject.save(project);
    }

    @PutMapping("{id}/tags")
    @Transactional
    public void addTagToProject(@PathVariable Long id, @RequestParam("tag") Tag tag) {
        EntityProject project = repositoryProject.getReferenceById(id);
        Set<Tag> tags = project.getTags();
        tags.add(tag);
        project.setTags(tags);
        repositoryProject.save(project);
    }

    @PutMapping("/activate/{id}")
    @Transactional
    public void activate(@PathVariable Long id) {
        var project = repositoryProject.getReferenceById(id);
        project.activate();
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public void inactivate(@PathVariable Long id) {
        var project = repositoryProject.getReferenceById(id);
        project.inactivate();
    }

    @DeleteMapping("{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        repositoryProject.deleteById(id);
    }
}