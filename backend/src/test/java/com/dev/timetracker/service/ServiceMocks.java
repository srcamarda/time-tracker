package com.dev.timetracker.service;

import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Role;
import com.dev.timetracker.utility.category.Tag;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class ServiceMocks {

    static EntityUser user1;
    static EntityUser user2;

    static EntityTask task1;
    static EntityTask task2;
    static EntityTask task3;

    static EntityProject project1;
    static EntityProject project2;

    static LocalDate startDate;
    static LocalDate endDate;
    static Timestamp startTimestamp;
    static Timestamp endTimestamp;

    public static void userMocks() {

        user1 = new EntityUser(
                1L,
                "davicjr",
                "Davi Costa",
                "12345678901",
                "davicjr@gmail.com",
                Role.JUNIOR,
                "45205037",
                null,
                null,
                null,
                null,
                null,
                null,
                true);

        user2 = new EntityUser(
                2L,
                "mariabonita",
                "Maria Bonita",
                "12345678902",
                "mariabonita@gmail.com",
                Role.PLENO,
                "45200000",
                null,
                null,
                null,
                null,
                null,
                null,
                true);
    }

    public static void taskMocks() {

        task1 = new EntityTask(1L,
                "Task 1",
                "Description 1",
                Tag.URGENT,
                user1,
                new Timestamp(1620000000000L),
                new Timestamp(1620007200000L),
                true, project1);

        task2 = new EntityTask(2L,
                "Task 2",
                "Description 2",
                Tag.IN_PROGRESS,
                user2,
                new Timestamp(1620000000000L),
                new Timestamp(1620003600000L),
                true, project1);

        task3 = new EntityTask(3L,
                "Task 3",
                "Description 3",
                Tag.COMPLETE,
                user1,
                new Timestamp(1620010800000L),
                new Timestamp(1620028000000L),
                true, project1);
    }

    public static void projectMocks() {

        project1 = new EntityProject(1L,
                "Project 1",
                "Description 1",
                new Timestamp(1620000000000L),
                new Timestamp(1620007200000L),
                true,
                new HashSet<>(Collections.singletonList(user1)),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(Tag.URGENT, Tag.IN_PROGRESS)));

        project2 = new EntityProject(2L,
                "Project 2",
                "Description 2",
                new Timestamp(1620000000000L),
                new Timestamp(1620017200000L),
                true,
                new HashSet<>(Arrays.asList(user1, user2)),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(Tag.REVIEW, Tag.COMPLETE)));

    }

    public static void initializeVariables() {
        startDate = LocalDate.of(2021, 5, 1);
        endDate = LocalDate.of(2021, 5, 2);
        startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));
    }


}