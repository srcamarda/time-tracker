package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        List<EntityTask> tasks = List.of(task, task2);
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
        verifyNoMoreInteractions(repositoryTask);
    }

    @Test
    void calculateUserTimeRankingForProject_ShouldReturnEmptyListWhenNoTasks() {
        // Arrange
        when(repositoryTask.findAllByProjectIdAndActiveTrue(1L)).thenReturn(List.of());

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(1L);

        // Assert
        assertEquals(0, result.size(), "Lista vazia quando não há tarefas");

        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(1L);
        verifyNoMoreInteractions(repositoryTask);
    }

    @Test
    void calculateUserTimeRankingForProject_ShouldReturnEmptyListForNonExistentProject() {
        // Arrange
        when(repositoryTask.findAllByProjectIdAndActiveTrue(999L)).thenReturn(List.of());

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(999L);

        // Assert
        assertEquals(0, result.size(), "Lista vazia quando o projeto não existe");

        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(999L);
        verifyNoMoreInteractions(repositoryTask);
    }
}