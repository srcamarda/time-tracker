package com.dev.timetracker.mocks;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;


public class VariablesMocks {

    public static LocalDate startDate;
    public static LocalDate endDate;
    public static Timestamp startTimestamp;
    public static Timestamp endTimestamp;

    public static void initializeVariables() {
        startDate = LocalDate.of(2021, 5, 1);
        endDate = LocalDate.of(2021, 5, 2);
        startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));
    }

}