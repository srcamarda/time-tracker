package com.dev.timetracker.dto.project;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record DTOUpdateProject(

        @NotNull
        Long id,
        String title,
        String description,
        Timestamp startTime,
        Timestamp endTime) {
}