package com.dev.timetracker.controller;

import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static com.dev.timetracker.mocks.ProjectMocks.projectMock;
import static com.dev.timetracker.mocks.ProjectMocks.projectMocks;
import static com.dev.timetracker.mocks.TaskMocks.*;
import static com.dev.timetracker.mocks.UserMocks.userMocks;
import static com.dev.timetracker.security.SecurityConfigTest.basicUser;
import static com.dev.timetracker.security.SecurityConfigTest.mockLogin;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeAll
    public static void initializeTestMocks() {
        userMocks();
        taskMocks();
        projectMocks();
    }

    @BeforeEach
    public void setup() {
        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mockLogin);
    }

    @Test
    public void shouldCreateNewTask() throws Exception {

        Mockito.when(repositoryTask.findByIdAndActiveTrue(task.getId())).thenReturn(task);

        String requestJson = objectMapper.writeValueAsString(taskDTO);

        mockMvc.perform(post("/tasks")
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        EntityTask newTask = repositoryTask.findByIdAndActiveTrue(task.getId());
        Assertions.assertEquals(task, newTask);
    }

    @Test
    public void getShouldReturnTask() throws Exception {

        Mockito.when(repositoryTask.findByIdAndActiveTrue(task.getId())).thenReturn(task);

        String responseJson = objectMapper.writeValueAsString(new DTOListTask(task));

        mockMvc.perform(get("/tasks/" + task.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listShouldReturnTask() throws Exception {

        Page<EntityTask> tasks = new PageImpl<>(List.of(task, task2));

        Mockito.when(repositoryTask.findAllByActiveTrue(Mockito.any(Pageable.class))).thenReturn(tasks);

        String responseJson = objectMapper.writeValueAsString(tasks.map(DTOListTask::new).getContent());

        mockMvc.perform(get("/tasks")
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listAllShouldReturnTasks() throws Exception {

        Page<EntityTask> tasks = new PageImpl<>(List.of(task, task2));

        Mockito.when(repositoryTask.findAllByActiveTrue(Mockito.any(Pageable.class))).thenReturn(tasks);

        String responseJson = objectMapper.writeValueAsString(tasks.map(DTOListTask::new).getContent());

        mockMvc.perform(get("/tasks/all")
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
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
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldActivateTask() throws Exception {

        Mockito.when(repositoryTask.getReferenceById(taskMock.getId())).thenReturn(taskMock);

        mockMvc.perform(put("/tasks/activate/" + taskMock.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(taskMock, Mockito.times(1)).activate();
    }

    @Test
    public void shouldInactivateTask() throws Exception {

        Mockito.when(repositoryTask.getReferenceById(taskMock.getId())).thenReturn(taskMock);

        //When user is inactivated, it should return ok
        mockMvc.perform(put("/tasks/inactivate/" + taskMock.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        //Inactivate user must be called 1 time
        Mockito.verify(taskMock, Mockito.times(1)).inactivate();
    }

    @Test
    public void shouldDeleteTask() throws Exception {

        Mockito.when(repositoryTask.getReferenceById(task.getId())).thenReturn(taskMock);
        Mockito.when(repositoryProject.findByTasksId(task.getId())).thenReturn(List.of(projectMock));

        mockMvc.perform(delete("/tasks/" + task.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(repositoryTask, Mockito.times(1)).deleteById(task.getId());
        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }
}