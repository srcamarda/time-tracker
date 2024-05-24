package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.utility.category.Tag;
import com.dev.timetracker.utility.category.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculateUserTimeRankingForProject {

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
                new Timestamp(1620018000000L),
                true, project1);
    }

    @Test
    void calculateUserTimeRankingForProject_ShouldReturnSortedRanking() {
        // Arrange

        List<EntityTask> tasks = Arrays.asList(task1, task2, task3);
        when(repositoryTask.findAllByProjectIdAndActiveTrue(1L)).thenReturn(tasks);

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(1L);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Davi Costa", result.get(0).userName());
        assertEquals(4, result.get(0).totalHours());
        assertEquals("Maria Bonita", result.get(1).userName());
        assertEquals(1, result.get(1).totalHours());

        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(1L);
    }
    @Test
    void calculateUserTimeRankingForProject_ShouldIgnoreInactiveTasks() {
        // Arrange
        // Modificar task1 para ser inativa
        task1.setActive(false);

        // Lista de tarefas inclui task1 inativa
        List<EntityTask> tasks = Arrays.asList(task2, task3);
        when(repositoryTask.findAllByProjectIdAndActiveTrue(1L)).thenReturn(tasks);

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(1L);

        // Assert
        assertEquals("Davi Costa", result.get(0).userName());
        assertEquals(2, result.get(0).totalHours());

        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(1L);
    }

}
