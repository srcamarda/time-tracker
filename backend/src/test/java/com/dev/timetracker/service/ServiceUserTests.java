package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.utility.category.Tag;
import com.dev.timetracker.utility.category.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceUserTests {

    @Mock
    private RepositoryTask repositoryTask;

    @InjectMocks
    private ReportService reportService;  // Supondo que o método está nesta classe

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void calculateUserTimeRankingForProject_ShouldReturnSortedRanking() {
        // Arrange
        EntityUser user1 = new EntityUser(1L,
                "davicjr",
                "Davi Costa",
                "12345678901",
                "davicjr@gmail.com",
                Role.JUNIOR,
                "12345",
                "Country1",
                "State1",
                "City1",
                "Street1", "District1", 100, true);
        EntityUser user2 = new EntityUser(2L, "mariabonita", "Maria Bonita", "12345678902", "mariabonita@gmail.com", Role.PLENO, "54321", "Country2", "State2", "City2", "Street2", "District2", 200, true);

        EntityProject project = new EntityProject(1L, "Project 1", "Description 1", new Timestamp(1620000000000L), new Timestamp(1620007200000L), true,
                new HashSet<>(Arrays.asList(user1, user2)), new HashSet<>(), new HashSet<>(Arrays.asList(Tag.URGENT, Tag.IN_PROGRESS)));

        EntityTask task1 = new EntityTask(1L, "Task 1", "Description 1", Tag.URGENT, user1, new Timestamp(1620000000000L), new Timestamp(1620007200000L), true, project);
        EntityTask task2 = new EntityTask(2L, "Task 2", "Description 2", Tag.IN_PROGRESS, user2, new Timestamp(1620000000000L), new Timestamp(1620003600000L), true, project);
        EntityTask task3 = new EntityTask(3L, "Task 3", "Description 3", Tag.COMPLETE, user1, new Timestamp(1620010800000L), new Timestamp(1620018000000L), true, project);

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

}
