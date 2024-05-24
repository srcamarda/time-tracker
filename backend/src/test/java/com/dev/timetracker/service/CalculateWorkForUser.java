package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOTimeWork;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.utility.category.Tag;
import com.dev.timetracker.utility.category.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CalculateWorkForUser {

    @Mock
    private RepositoryTask repositoryTask;

    @InjectMocks
    private ReportService reportService;

    static EntityUser user1;
    static EntityUser user2;

    static EntityTask task1;
    static EntityTask task2;
    static EntityTask task3;

    static EntityProject project1;

    @BeforeAll
    public static void initializeTestData() {
        // Initialize Users
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

        // Initialize Project
        project1 = new EntityProject(1L,
                "Project 1",
                "Description 1",
                new Timestamp(1620000000000L),
                new Timestamp(1620007200000L),
                true,
                new HashSet<>(Arrays.asList(user1, user2)),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(Tag.URGENT, Tag.IN_PROGRESS)));

        // Initialize Tasks
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

    @Test
    void calculateWorkForUser_ShouldReturnCorrectWorkHours() {
        LocalDate startDate = LocalDate.of(2021, 5, 1);
        LocalDate endDate = LocalDate.of(2021, 5, 2);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));


        List<EntityTask> tasks = Arrays.asList(task1, task3);
        when(repositoryTask.findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user1.getId()), eq(startTimestamp), eq(endTimestamp)))
                .thenReturn(tasks);

        // Act
        List<DTOTimeWork> result = reportService.calculateWorkForUser(user1.getId(), startDate, endDate);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).projectName());
        assertEquals("Task 1", result.get(0).taskName());
        assertEquals(2, result.get(0).totalHours());
        assertEquals("Project 1", result.get(1).projectName());
        assertEquals("Task 3", result.get(1).taskName());
        assertEquals(4, result.get(1).totalHours());

        verify(repositoryTask, times(1)).findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user1.getId()), eq(startTimestamp), eq(endTimestamp));
    }
    @Test
    void calculateWorkForUser_ShouldReturnEmptyListWhenNoTasks() {
        // Arrange
        LocalDate startDate = LocalDate.of(2021, 5, 1);
        LocalDate endDate = LocalDate.of(2021, 5, 2);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));

        when(repositoryTask.findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user1.getId()), eq(startTimestamp), eq(endTimestamp)))
                .thenReturn(Collections.emptyList());

        // Act
        List<DTOTimeWork> result = reportService.calculateWorkForUser(user1.getId(), startDate, endDate);

        // Assert
        assertEquals(0, result.size());
        verify(repositoryTask, times(1)).findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user1.getId()), eq(startTimestamp), eq(endTimestamp));
    }
    @Test
    void calculateWorkForUser_ShouldReturnEmptyListForNonExistentUser() {
        // Arrange
        LocalDate startDate = LocalDate.of(2021, 5, 1);
        LocalDate endDate = LocalDate.of(2021, 5, 2);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));

        when(repositoryTask.findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(999L), eq(startTimestamp), eq(endTimestamp)))
                .thenReturn(Collections.emptyList());

        // Act
        List<DTOTimeWork> result = reportService.calculateWorkForUser(999L, startDate, endDate);

        // Assert
        assertEquals(0, result.size());
        verify(repositoryTask, times(1)).findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(999L), eq(startTimestamp), eq(endTimestamp));
    }
}
