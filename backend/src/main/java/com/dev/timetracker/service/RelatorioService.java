package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOTimeWork;
import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private RepositoryTask repositoryTask;

    public List<DTOUserTime> calculateUserTimeRankingForProject(Long projectId) {
        List<EntityTask> tasks = repositoryTask.findAllByProjectIdAndActiveTrue(projectId);
        Map<EntityUser, Long> userTimeMap = new HashMap<>();

        for (EntityTask task : tasks) {
            long duration = TimeUnit.MILLISECONDS.toHours(task.getEndTime().getTime() - task.getStartTime().getTime());
            userTimeMap.merge(task.getIdUser(), duration, Long::sum);
        }

        return userTimeMap.entrySet().stream()
                .map(entry -> new DTOUserTime(entry.getKey().getName(), entry.getValue()))
                .sorted(Comparator.comparing(DTOUserTime::totalHours).reversed())
                .collect(Collectors.toList());
    }
    public List<DTOTimeWork> calculateWorkForUser(Long userId, LocalDate startDate, LocalDate endDate) {
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));

        List<EntityTask> tasks = repositoryTask.findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                userId, startTimestamp, endTimestamp);

        List<DTOTimeWork> report = new ArrayList<>();
        for (EntityTask task : tasks) {
            long duration = task.getEndTime().getTime() - task.getStartTime().getTime();
            long hours = TimeUnit.MILLISECONDS.toHours(duration);

            DTOTimeWork dto = new DTOTimeWork(task.getProject().getTitle(), task.getTitle(), hours);
            report.add(dto);
        }
        return report;
    }
}