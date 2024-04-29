package com.dev.timetracker.controller;

import com.dev.timetracker.dto.report.DTOAverageTime;
import com.dev.timetracker.dto.report.DTOProjectTime;
import com.dev.timetracker.dto.report.DTOTimeWork;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOUpdateUser;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.service.RelatorioService;
import com.dev.timetracker.utility.UniqueException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private RelatorioService relatorioService;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid DTOCreateUser data) {
        if (repositoryUser.existsByUsername(data.username()))
            throw new UniqueException("Username already exists", "username");

        if (repositoryUser.existsByCpf(data.cpf()))
            throw new UniqueException("Cpf already exists", "cpf");

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

    @GetMapping("/{userId}/hours")
    public ResponseEntity<List<DTOProjectTime>> getHoursWorkedForUser(@PathVariable Long userId) {
        List<DTOProjectTime> hoursWorked = relatorioService.hoursWorkedByUserAllProjects(userId);
        return ResponseEntity.ok(hoursWorked);
    }

    @GetMapping("/{userId}/average-task-time")
    public ResponseEntity<DTOAverageTime> getAverageTaskTime(@PathVariable Long userId) {
        DTOAverageTime averageTime = relatorioService.hoursWorkedByUserAverageTime(userId);
        return ResponseEntity.ok(averageTime);
    }

    @GetMapping("/{userId}/work-report")
    public ResponseEntity<List<DTOTimeWork>> getWorkReportForUser(
            @PathVariable Long userId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<DTOTimeWork> report = relatorioService.calculateWorkForUser(userId, startDate, endDate);
        if (report.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(report);
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