package com.dev.timetracker.dto.task;

import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;

import java.sql.Timestamp;

public record DTOListTask(

        Long id,
        String title,
        String description,
        Tag tag,
        EntityUser idUser,
        Timestamp startTime,
        Timestamp endTime,
        Boolean active) {

    public DTOListTask(EntityTask task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTag(),
                task.getIdUser(),
                task.getStartTime(),
                task.getEndTime(),
                task.getActive());
    }
}