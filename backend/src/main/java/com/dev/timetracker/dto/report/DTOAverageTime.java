package com.dev.timetracker.dto.report;

public record DTOAverageTime(
        Long quantity,
        Long totalHours,
        Double average
) {
}
