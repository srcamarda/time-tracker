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
        EntityUser id_user,
        Timestamp start_time,
        Timestamp end_time,
        Boolean active) {

    public DTOListTask(EntityTask task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTag(),
                task.getId_user(),
                task.getStart_time(),
                task.getEnd_time(),
                task.getActive());
    }
}