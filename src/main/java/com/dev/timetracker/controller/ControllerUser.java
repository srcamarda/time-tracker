package com.dev.timetracker.controller;

import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOUpdateUser;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryTask repositoryTask;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid DTOCreateUser data) {
        repositoryUser.save(new EntityUser(data));
    }

    @GetMapping("{id}")
    public DTOListUser get(@PathVariable Long id) {
        return new DTOListUser(repositoryUser.findByIdAndActiveTrue(id));
    }

    @GetMapping
    public List<DTOListUser> list(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        return repositoryUser.findAllByActiveTrue(pageable).map(DTOListUser::new).getContent();
    }

    @GetMapping("all")
    public List<DTOListUser> listAll(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return repositoryUser.findAllByActiveTrue(pageable).map(DTOListUser::new).getContent();
    }

    @GetMapping("{id}/tasks")
    public List<DTOListTask> listTasks(@PathVariable Long id, @PageableDefault(sort = {"id"}) Pageable pageable) {
        EntityUser user = repositoryUser.getReferenceById(id);
        return repositoryTask.findAllByIdUserAndActiveTrue(user, pageable).map(DTOListTask::new).getContent();
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateUser data) {
        var user = repositoryUser.getReferenceById(data.id());
        user.update(data);
    }

    @PutMapping("/activate/{id}")
    @Transactional
    public void activate(@PathVariable Long id) {
        var user = repositoryUser.getReferenceById(id);
        user.activate();
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public void inactivate(@PathVariable Long id) {
        var user = repositoryUser.getReferenceById(id);
        user.inactivate();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        repositoryUser.deleteById(id);
    }
}