package com.dev.timetracker.dto.project;

import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.utility.category.Tag;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

public record DTOListProject(

        Long id,
        String title,
        String description,
        Timestamp startTime,
        Timestamp endTime,
        Set<DTOListUser> users,
        Set<DTOListTask> tasks,
        Set<Tag> tags) {

    public DTOListProject(EntityProject project) {
        this(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStartTime(),
                project.getEndTime(),
                project.getUsers().stream().map(DTOListUser::new).collect(Collectors.toSet()),
                project.getTasks().stream().map(DTOListTask::new).collect(Collectors.toSet()),
                project.getTags());
    }
}