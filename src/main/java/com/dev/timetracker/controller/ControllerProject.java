package com.dev.timetracker.controller;

import com.dev.timetracker.dto.project.DTOCreateProject;
import com.dev.timetracker.dto.project.DTOListProject;
import com.dev.timetracker.dto.project.DTOUpdateProject;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.repository.RepositoryProject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
public class ControllerProject {

    @Autowired
    private RepositoryProject repositoryProject;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid DTOCreateProject data) {
        repositoryProject.save(new EntityProject(data));
    }

    @GetMapping({"{id}"})
    public DTOListProject get(@PathVariable Long id) {
        return new DTOListProject(repositoryProject.getReferenceById(id));
    }

    @GetMapping
    public List<DTOListProject> list(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        return repositoryProject.findAllByActiveTrue(pageable).map(DTOListProject::new).getContent();
    }

    @GetMapping("all")
    public List<DTOListProject> listAll(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return repositoryProject.findAllByActiveTrue(pageable).map(DTOListProject::new).getContent();
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateProject data) {
        var project = repositoryProject.getReferenceById(data.id());
        project.update(data);
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