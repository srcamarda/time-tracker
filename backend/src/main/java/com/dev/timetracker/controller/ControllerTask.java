package com.dev.timetracker.controller;

import com.dev.timetracker.dto.task.DTOCreateTask;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class ControllerTask {

    @Autowired
    private RepositoryTask repositoryTask;
    @Autowired
    private RepositoryProject repositoryProject;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid DTOCreateTask data) {
        repositoryTask.save(new EntityTask(data));
    }

    @GetMapping("{id}")
    public DTOListTask get(@PathVariable Long id) {
        return new DTOListTask(repositoryTask.findByIdAndActiveTrue(id));
    }

    @GetMapping
    public List<DTOListTask> list(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return repositoryTask.findAllByActiveTrue(pageable).map(DTOListTask::new).getContent();
    }

    @GetMapping("all")
    public List<DTOListTask> listAll(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return repositoryTask.findAllByActiveTrue(pageable).map(DTOListTask::new).getContent();
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateTask data) {
        EntityTask task = repositoryTask.getReferenceById(data.id());
        task.update(data);
    }

    @PutMapping("/activate/{id}")
    @Transactional
    public void activate(@PathVariable Long id) {
        EntityTask task = repositoryTask.getReferenceById(id);
        task.activate();
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public void inactivate(@PathVariable Long id) {
        EntityTask task = repositoryTask.getReferenceById(id);
        task.inactivate();
    }

    @DeleteMapping("{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        EntityTask task = repositoryTask.getReferenceById(id);

        //Remove task from related projects
        List<EntityProject> projects = repositoryProject.findByTasksId(id);
        projects.forEach(project -> {
            project.getTasks().remove(task);
            repositoryProject.save(project);
        });

        repositoryTask.deleteById(id);
    }
}