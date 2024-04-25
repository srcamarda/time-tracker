package com.dev.timetracker.dto.task;

import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record DTOCreateTask(

        Long id,
        @NotBlank
        String title,
        String description,
        Tag tag,
        @NotNull
        EntityUser idUser,
        @NotNull
        Timestamp startTime,
        Timestamp endTime) {
}