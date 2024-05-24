package com.dev.timetracker.controller;

import com.dev.timetracker.entity.EntityTask;
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
import org.springframework.test.web.servlet.MockMvc;

import static com.dev.timetracker.controller.ControllerMocks.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @InjectMocks
    private ControllerTask controllerTask;

    @BeforeAll
    public static void initializeTestMocks() {
        userMocks();
        taskMocks();
    }

    @Test
    public void shouldCreateNewTask() throws Exception {

        Mockito.when(repositoryTask.findByIdAndActiveTrue(task.getId())).thenReturn(task);

        String requestJson = objectMapper.writeValueAsString(taskDTO);

        mockMvc.perform(post("/tasks")
                        .with(httpBasic("moana", "21055356070"))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        EntityTask newTask = repositoryTask.findByIdAndActiveTrue(task.getId());
        Assertions.assertEquals(task, newTask);
    }

    @Test
    public void shouldUpdateTask() throws Exception {

        Mockito.when(repositoryTask.findByIdAndActiveTrue(task.getId())).thenReturn(task);

        String requestJson = objectMapper.writeValueAsString(taskDTO);

        task.setEndTime(Timestamp.valueOf(LocalDateTime.now()));

        mockMvc.perform(put("/tasks")
                        .with(httpBasic("moana", "21055356070"))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
}