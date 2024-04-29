package com.dev.timetracker.dto.report;

public record DTOTimeWork (
        String projectName,
        String taskName,
        Long totalHours
){
}
