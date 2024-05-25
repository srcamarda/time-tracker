package com.dev.timetracker.service;
import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.repository.RepositoryTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.dev.timetracker.mocks.MocksForTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceCalculateUserTimeRankingForProject {

    @Mock
    private RepositoryTask repositoryTask;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    public void initializeTestData() {
        userMocks();
        projectMocks();
        taskMocks();
    }


    @Test
    void calculateUserTimeRankingForProject_ShouldReturnSortedRanking() {
        // Arrange
        List<EntityTask> tasks = Arrays.asList(task, task2);
        when(repositoryTask.findAllByProjectIdAndActiveTrue(1L)).thenReturn(tasks);

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(1L);

        // Assert
        assertAll("result",
                () -> assertEquals(2, result.size(), "Tamanho da lista"),
                () -> assertEquals("Thiago Silveira", result.get(0).userName(), "Nome do usuário 1"),
                () -> assertEquals(2, result.get(0).totalHours(), "Total de horas do usuário 1"),
                () -> assertEquals("Joao Batista", result.get(1).userName(), "Nome do usuário 2"),
                () -> assertEquals(1, result.get(1).totalHours(), "Total de horas do usuário 2")
        );

        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(1L);
    }

    @Test
    void calculateUserTimeRankingForProject_ShouldIgnoreInactiveTasks() {
        // Arrange
        // Modificar task para ser inativa
        task.setActive(false);

        // Lista de tarefas inclui apenas a task ativa
        List<EntityTask> tasks = List.of(task2);
        when(repositoryTask.findAllByProjectIdAndActiveTrue(1L)).thenReturn(tasks);

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(1L);

        // Assert
        assertAll("result",
                () -> assertEquals(1, result.size(), "Tamanho da lista"),
                () -> assertEquals("Joao Batista", result.get(0).userName(), "Nome do usuário"),
                () -> assertEquals(1, result.get(0).totalHours(), "Total de horas do usuário")
        );

        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(1L);
    }
}