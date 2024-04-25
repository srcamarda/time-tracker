package com.dev.timetracker.dto.task;

import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record DTOUpdateTask(

    @NotNull
    Long id,
    String title,
    String description,
    Tag tag,
    EntityUser id_user,
    Timestamp start_time,
    Timestamp end_time) {
}