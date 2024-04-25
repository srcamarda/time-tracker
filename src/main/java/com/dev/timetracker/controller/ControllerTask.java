package com.dev.timetracker.controller;

import com.dev.timetracker.dto.task.DTOCreateTask;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.entity.EntityTask;
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
    public List<DTOListTask> list(@PageableDefault(size = 5, sort ={"title"}) Pageable pageable) {
        return repositoryTask.findAllByActiveTrue(pageable).map(DTOListTask::new).getContent();
    }

    @GetMapping("all")
    public List<DTOListTask> listAll(@PageableDefault(sort ={"title"}) Pageable pageable) {
        return repositoryTask.findAllByActiveTrue(pageable).map(DTOListTask::new).getContent();
    }

//    @GetMapping("{id_user}")
//    public List<DTOListTask> listByUser(@PageableDefault(size = 5, sort ={"title"}) Pageable pageable, @PathVariable Long id_user) {
//        return repositoryTask.findAllById_userAndActiveTrue(id_user, pageable).map(DTOListTask::new).getContent();
//    }
//
//    @GetMapping("all/{id_user}")
//    public List<DTOListTask> listAllByUser(@PageableDefault(sort ={"title"}) Pageable pageable, @PathVariable Long id_user) {
//        return repositoryTask.findAllById_userAndActiveTrue(id_user, pageable).map(DTOListTask::new).getContent();
//    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateTask data) {
        var task = repositoryTask.getReferenceById(data.id());
        task.update(data);
    }

    @PutMapping("/activate/{id}")
    @Transactional
    public void activate(@PathVariable Long id) {
        var task = repositoryTask.getReferenceById(id);
        task.activate();
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public void inactivate(@PathVariable Long id) {
        var task = repositoryTask.getReferenceById(id);
        task.inactivate();
    }

    @DeleteMapping("{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        var task = repositoryTask.getReferenceById(id);
        task.inactivate();
    }
}