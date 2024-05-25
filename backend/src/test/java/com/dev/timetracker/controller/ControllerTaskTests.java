package com.dev.timetracker.controller;

import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static com.dev.timetracker.Mocks.MocksForTest.*;
import static com.dev.timetracker.security.SecurityConfigTest.basicUser;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ControllerTaskTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryUser repositoryUser;

    @MockBean
    private RepositoryTask repositoryTask;

    @MockBean
    private RepositoryProject repositoryProject;

    @InjectMocks
    private ControllerTask controllerTask;

    @BeforeAll
    public static void initializeTestMocks() {
        userMocks();
        taskMocks();
        projectMocks();
    }

    @Test
    public void shouldCreateNewTask() throws Exception {

        Mockito.when(repositoryTask.findByIdAndActiveTrue(task.getId())).thenReturn(task);

        String requestJson = objectMapper.writeValueAsString(taskDTO);

        mockMvc.perform(post("/tasks")
                        .with(httpBasic(basicUser.username(), basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        EntityTask newTask = repositoryTask.findByIdAndActiveTrue(task.getId());
        Assertions.assertEquals(task, newTask);
    }

    @Test
    public void shouldUpdateTask() throws Exception {

        DTOUpdateTask updateTask = new DTOUpdateTask(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTag(),
                task.getIdUser(),
                task.getStartTime(),
                task.getEndTime()
        );

        Mockito.when(repositoryTask.getReferenceById(task.getId())).thenReturn(taskMock);

        String requestJson = objectMapper.writeValueAsString(updateTask);

        mockMvc.perform(put("/tasks")
                        .with(httpBasic(basicUser.username(), basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

//        Mockito.verify(taskMock, Mockito.times(1)).update(updateTask);
    }

    @Test
    public void shouldActivateTask() throws Exception {

        Mockito.when(repositoryTask.getReferenceById(taskMock.getId())).thenReturn(taskMock);

        mockMvc.perform(put("/tasks/activate/" + taskMock.getId())
                        .with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(taskMock, Mockito.times(1)).activate();
    }

    @Test
    public void shouldInactivateTask() throws Exception {

        Mockito.when(repositoryTask.getReferenceById(taskMock.getId())).thenReturn(taskMock);

        //When user is inactivated, it should return ok
        mockMvc.perform(put("/tasks/inactivate/" + taskMock.getId())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());

        //Inactivate user must be called 1 time
        Mockito.verify(taskMock, Mockito.times(1)).inactivate();
    }

    @Test
    public void shouldDeleteTask() throws Exception {

        Mockito.when(repositoryTask.getReferenceById(task.getId())).thenReturn(taskMock);
        Mockito.when(repositoryProject.findByTasksId(task.getId())).thenReturn(List.of(projectMock));

        mockMvc.perform(delete("/tasks/" + task.getId())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());


        Mockito.verify(repositoryTask, Mockito.times(1)).deleteById(task.getId());
        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }
}