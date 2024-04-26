package com.dev.timetracker.dto.task;

import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.utility.category.Tag;

import java.sql.Timestamp;

public record DTOListTask(

        Long id,
        String title,
        String description,
        Tag tag,
        DTOListUser idUser,
        Timestamp startTime,
        Timestamp endTime) {

    public DTOListTask(EntityTask task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTag(),
                new DTOListUser(task.getIdUser()),
                task.getStartTime(),
                task.getEndTime());
    }
}