package com.dev.timetracker.controller;

import com.dev.timetracker.dto.project.DTOCreateProject;
import com.dev.timetracker.dto.project.DTOListProject;
import com.dev.timetracker.dto.project.DTOUpdateProject;
import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.service.ReportService;
import com.dev.timetracker.utility.category.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("projects")
public class ControllerProject {

    @Autowired
    private RepositoryProject repositoryProject;
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryTask repositoryTask;
    @Autowired
    private ReportService reportService;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid DTOCreateProject data) {
        repositoryProject.save(new EntityProject(data));
    }

    @GetMapping({"{id}"})
    public DTOListProject get(@PathVariable Long id) {
        return new DTOListProject(repositoryProject.findByIdAndActiveTrue(id));
    }

    @GetMapping("/users/{userId}")
    public List<DTOListProject> getProjectsByUserId(@PathVariable Long userId) {
        List<EntityProject> projects = repositoryProject.findByUsersId(userId);
        return projects.stream().map(DTOListProject::new).collect(Collectors.toList());
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
    public ResponseEntity<List<DTOListTask>> listTasks(@PathVariable Long id) {
        List<EntityTask> tasks = repositoryTask.findByProjectIdAndActiveTrue(id);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<DTOListTask> dtoTasks = tasks.stream().map(DTOListTask::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtoTasks);
    }

    @GetMapping("/{projetoId}/users/report")
    public ResponseEntity<List<DTOUserTime>> getRankingUsuariosNoProjeto(@PathVariable Long projetoId) {
        List<DTOUserTime> ranking = reportService.calculateUserTimeRankingForProject(projetoId);
        return ResponseEntity.ok(ranking);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateProject data) {
        var project = repositoryProject.getReferenceById(data.id());
        project.update(data);
    }

    @PutMapping("{id}/users/{idUser}")
    @Transactional
    public void addUserToProject(@PathVariable Long id, @PathVariable Long idUser) {
        var project = repositoryProject.getReferenceById(id);
        var user = repositoryUser.getReferenceById(idUser);
        Set<EntityUser> users = project.getUsers();
        users.add(user);
        project.setUsers(users);
        repositoryProject.save(project);
    }

    @PutMapping("{id}/tasks/{idTask}")
    @Transactional
    public ResponseEntity<Void> addTaskToProject(@PathVariable Long id, @PathVariable Long idTask) {
        EntityProject project = repositoryProject.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        EntityTask task = repositoryTask.findById(idTask)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setProject(project);
        repositoryTask.save(task);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/tags")
    @Transactional
    public void addTagToProject(@PathVariable Long id, @RequestParam("tag") Tag tag) {
        var project = repositoryProject.getReferenceById(id);
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

    @DeleteMapping("{id}/users/{idUser}")
    @Transactional
    public void deleteUserFromProject(@PathVariable Long id, @PathVariable Long idUser) {
        var project = repositoryProject.getReferenceById(id);
        var user = repositoryUser.getReferenceById(idUser);
        project.getUsers().remove(user);
        repositoryProject.save(project);
    }

    @DeleteMapping("{id}/tasks/{idTask}")
    @Transactional
    public void deleteTaskFromProject(@PathVariable Long id, @PathVariable Long idTask) {
        var project = repositoryProject.getReferenceById(id);
        var task = repositoryTask.getReferenceById(idTask);
        project.getTasks().remove(task);
        repositoryProject.save(project);
    }

    @DeleteMapping("{id}/tags")
    @Transactional
    public void deleteTagFromProject(@PathVariable Long id, @RequestParam("tag") Tag tag) {
        var project = repositoryProject.getReferenceById(id);
        Set<Tag> tags = project.getTags();
        tags.remove(tag);
        project.setTags(tags);
        repositoryProject.save(project);
    }
}