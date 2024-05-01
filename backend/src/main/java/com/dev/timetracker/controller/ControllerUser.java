package com.dev.timetracker.controller;

import com.dev.timetracker.dto.report.DTOAverageTime;
import com.dev.timetracker.dto.report.DTOProjectTime;
import com.dev.timetracker.dto.report.DTOTimeWork;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOUpdateUser;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.service.ReportService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryTask repositoryTask;
    @Autowired
    private RepositoryProject repositoryProject;
    @Autowired
    private ReportService reportService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Void> register(@RequestBody @Valid DTOCreateUser data) {
        if (repositoryUser.existsByUsername(data.username()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");

        if (repositoryUser.existsByCpf(data.cpf()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cpf already exists");

        repositoryUser.save(new EntityUser(data));
        return ResponseEntity.ok().build();
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

    @GetMapping("/{userId}/hours")
    public ResponseEntity<List<DTOProjectTime>> getHoursWorkedForUser(@PathVariable Long userId) {
        List<DTOProjectTime> hoursWorked = reportService.hoursWorkedByUserAllProjects(userId);
        return ResponseEntity.ok(hoursWorked);
    }

    @GetMapping("/{userId}/average-task-time")
    public ResponseEntity<DTOAverageTime> getAverageTaskTime(@PathVariable Long userId) {
        DTOAverageTime averageTime = reportService.hoursWorkedByUserAverageTime(userId);
        return ResponseEntity.ok(averageTime);
    }

    @GetMapping("/{userId}/work-report")
    public ResponseEntity<List<DTOTimeWork>> getWorkReportForUser(
            @PathVariable Long userId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<DTOTimeWork> report = reportService.calculateWorkForUser(userId, startDate, endDate);
        if (report.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(report);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateUser data) {
        EntityUser user = repositoryUser.getReferenceById(data.id());
        user.update(data);
    }

    @PutMapping("/activate/{id}")
    @Transactional
    public void activate(@PathVariable Long id) {
        EntityUser user = repositoryUser.getReferenceById(id);
        user.activate();
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public void inactivate(@PathVariable Long id) {
        EntityUser user = repositoryUser.getReferenceById(id);
        user.inactivate();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        EntityUser user = repositoryUser.getReferenceById(id);

        //Remove user from related tasks
        List<EntityTask> tasks = repositoryTask.findAllByIdUser(user);
        tasks.forEach(task -> {
            task.setIdUser(null);
            repositoryTask.save(task);
        });

        //Remove user from related projects
        List<EntityProject> projects = repositoryProject.findByUsersId(id);
        projects.forEach(project -> {
            project.getUsers().remove(user);
            repositoryProject.save(project);
        });

        repositoryUser.deleteById(id);
    }
}