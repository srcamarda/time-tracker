package com.dev.timetracker.dto.project;

import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Set;

public record DTOCreateProject(

        Long id,
        @NotBlank
        String title,
        String description,
        @NotNull
        Timestamp startTime,
        Timestamp endTime,

        Set<EntityUser> users,
        Set<EntityTask> tasks,
        Set<Tag> tags) {
}