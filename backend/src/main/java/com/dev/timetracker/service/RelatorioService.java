package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}