package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOAverageTime;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.dev.timetracker.mocks.ProjectMocks.projectMocks;
import static com.dev.timetracker.mocks.TaskMocks.*;
import static com.dev.timetracker.mocks.UserMocks.user;
import static com.dev.timetracker.mocks.UserMocks.userMocks;
import static com.dev.timetracker.mocks.VariablesMocks.initializeVariables;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceHoursWorkedByUserAverageTime {

    @Mock
    private RepositoryTask repositoryTask;

    @Mock
    private RepositoryUser repositoryUser;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    public void initializeTestData() {
        userMocks();
        projectMocks();
        taskMocks();
        initializeVariables();
    }

    @Test
    void hoursWorkedByUserAverageTime_ShouldReturnCorrectAveragesForMultipleActiveTasks() {
        // Arrange
        when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
        when(repositoryTask.findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task, task2)));

        // Act
        DTOAverageTime result = reportService.hoursWorkedByUserAverageTime(user.getId());

        // Assert
        assertAll("result",
                () -> assertEquals(2, result.quantity(), "Quantidade de tarefas"),
                () -> assertEquals(3, result.totalHours(), "Total de horas"),
                () -> assertEquals(1.5, result.average(), "Média de horas")
        );

        verify(repositoryUser, times(1)).getReferenceById(user.getId());
        verify(repositoryTask, times(1)).findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class));
    }

    @Test
    void hoursWorkedByUserAverageTime_ShouldConsiderOnlyActiveTasks() {
        // Arrange
        task.setActive(false); // Torna uma das tarefas inativa
        task2.setActive(true); // Garante que a segunda tarefa esteja ativa
        List<EntityTask> tasks = List.of(task, task2);
        when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
        when(repositoryTask.findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task2))); // Retorna apenas a tarefa ativa

        // Act
        DTOAverageTime result = reportService.hoursWorkedByUserAverageTime(user.getId());

        // Assert
        assertAll("result",
                () -> assertEquals(1, result.quantity(), "Quantidade de tarefas ativas"),
                () -> assertEquals(1, result.totalHours(), "Total de horas das tarefas ativas"),
                () -> assertEquals(1.0, result.average(), "Média de horas das tarefas ativas")
        );

        verify(repositoryUser, times(1)).getReferenceById(user.getId());
        verify(repositoryTask, times(1)).findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class));
    }

    @Test
    void hoursWorkedByUserAverageTime_ShouldHandleNonExistentUser() {
        // Arrange
        when(repositoryUser.getReferenceById(999L)).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reportService.hoursWorkedByUserAverageTime(999L);
        });

        verify(repositoryUser, times(1)).getReferenceById(999L);
        verify(repositoryTask, never()).findAllByIdUserAndActiveTrue(any(EntityUser.class), any(Pageable.class));
    }



}

