package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOProjectTime;
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

import static com.dev.timetracker.mocks.ProjectMocks.project2;
import static com.dev.timetracker.mocks.ProjectMocks.projectMocks;
import static com.dev.timetracker.mocks.TaskMocks.*;
import static com.dev.timetracker.mocks.UserMocks.user;
import static com.dev.timetracker.mocks.UserMocks.userMocks;
import static com.dev.timetracker.mocks.VariablesMocks.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceHoursWorkedByUserAllProjects {

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
    void calculateHoursWorkedByUserAllProjects_ShouldReturnTotalHoursWorked() {
        // Arrange
        when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
        when(repositoryTask.findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task, task2)));

        // Act
        List<DTOProjectTime> result = reportService.hoursWorkedByUserAllProjects(user.getId());

        // Assert
        long totalHoursWorked = result.stream().mapToLong(DTOProjectTime::totalHours).sum();
        assertEquals(3, totalHoursWorked, "Total de horas trabalhadas em todos os projetos");

        verify(repositoryUser, times(1)).getReferenceById(user.getId());
        verify(repositoryTask, times(1)).findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class));
    }

    @Test
    void calculateHoursWorkedByUserAllProjects_ShouldReturnCorrectHoursSeparatedByProjects() {
        // Arrange
        task2.setProject(project2); // Associa a segunda tarefa ao segundo projeto
        when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
        when(repositoryTask.findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task, task2)));

        // Act
        List<DTOProjectTime> result = reportService.hoursWorkedByUserAllProjects(user.getId());

        // Assert
        assertEquals(2, result.size(), "Número de projetos");
        assertAll("result",
                () -> assertEquals("Moana", result.get(0).title(), "Nome do projeto 1"),
                () -> assertEquals(2, result.get(0).totalHours(), "Total de horas do projeto 1"),
                () -> assertEquals("Moana 2", result.get(1).title(), "Nome do projeto 2"),
                () -> assertEquals(1, result.get(1).totalHours(), "Total de horas do projeto 2")
        );
        verify(repositoryUser, times(1)).getReferenceById(user.getId());
        verify(repositoryTask, times(1)).findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class)); // Verificação diferenciada
        verifyNoMoreInteractions(repositoryUser, repositoryTask);
    }

    @Test
    void calculateHoursWorkedByUserAllProjects_ShouldIgnoreInactiveTasks() {
        // Arrange
        task2.setActive(false); // Torna a segunda tarefa inativa
        when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
        when(repositoryTask.findAllByIdUserAndActiveTrue(eq(user), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task)));
        // Act
        List<DTOProjectTime> result = reportService.hoursWorkedByUserAllProjects(user.getId());

        // Assert
        long totalHoursWorked = result.stream().mapToLong(DTOProjectTime::totalHours).sum();
        assertEquals(2, totalHoursWorked, "Total de horas trabalhadas em todos os projetos, excluindo tarefas inativas");

        verify(repositoryUser, times(1)).getReferenceById(user.getId());
        verify(repositoryTask, atLeast(1)).findAllByIdUserAndActiveTrue(eq(user), notNull());
        verifyNoMoreInteractions(repositoryUser, repositoryTask);

    }
}

