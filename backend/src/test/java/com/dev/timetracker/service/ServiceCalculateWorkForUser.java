package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOTimeWork;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.repository.RepositoryTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.dev.timetracker.mocks.MocksForTest.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceCalculateWorkForUser {

    @Mock
    private RepositoryTask repositoryTask;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    public void initializeTestData() {
        userMocks();
        projectMocks();
        taskMocks();
        initializeVariables();
    }

    private void initializeVariables() {
        startDate = LocalDate.of(2023, 5, 1);
        endDate = LocalDate.of(2023, 5, 31);
        startTimestamp = Timestamp.valueOf(LocalDateTime.of(startDate, LocalDateTime.MIN.toLocalTime()));
        endTimestamp = Timestamp.valueOf(LocalDateTime.of(endDate, LocalDateTime.MAX.toLocalTime()));
    }

    @Test
    void calculateWorkForUser_ShouldReturnCorrectWorkHours() {
        // Arrange
        List<EntityTask> tasks = List.of(task, task2);
        when(repositoryTask.findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user.getId()), eq(startTimestamp), eq(endTimestamp)))
                .thenReturn(tasks);

        // Act
        List<DTOTimeWork> result = reportService.calculateWorkForUser(user.getId(), startDate, endDate);

        // Assert
        // Assert
        assertAll("result",
                () -> assertEquals(2, result.size(), "Tamanho da lista"),
                () -> assertEquals("Moana", result.get(0).projectName(), "Nome do projeto 1"),
                () -> assertEquals("Creditos", result.get(0).taskName(), "Nome da tarefa 1"),
                () -> assertEquals(2, result.get(0).totalHours(), "Total de horas da tarefa 1"),
                () -> assertEquals("Moana 2", result.get(1).projectName(), "Nome do projeto 2"),
                () -> assertEquals("Legenda", result.get(1).taskName(), "Nome da tarefa 2"),
                () -> assertEquals(1, result.get(1).totalHours(), "Total de horas da tarefa 2")
        );

        verify(repositoryTask, times(1)).findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user.getId()), eq(startTimestamp), eq(endTimestamp));
    }

    @Test
    void calculateWorkForUser_ShouldReturnEmptyListWhenNoTasks() {

        when(repositoryTask.findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user.getId()), eq(startTimestamp), eq(endTimestamp)))
                .thenReturn(Collections.emptyList());

        // Act
        List<DTOTimeWork> result = reportService.calculateWorkForUser(user.getId(), startDate, endDate);

        // Assert
        assertEquals(0, result.size());
        verify(repositoryTask, times(1)).findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
                eq(user.getId()), eq(startTimestamp), eq(endTimestamp));
    }
    @Test
    void calculateWorkForUser_ShouldReturnEmptyListForNonExistentUser() {

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
