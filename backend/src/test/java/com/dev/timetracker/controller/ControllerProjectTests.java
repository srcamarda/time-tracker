package com.dev.timetracker.controller;

import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.dev.timetracker.mocks.ProjectMocks.*;
import static com.dev.timetracker.mocks.TaskMocks.taskMock;
import static com.dev.timetracker.mocks.TaskMocks.taskMocks;
import static com.dev.timetracker.mocks.UserMocks.userMocks;
import static com.dev.timetracker.security.SecurityConfigTest.basicUser;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ControllerProjectTests {

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

    @MockBean
    private ReportService reportService;

    @BeforeAll
    public static void initializeTestMocks() {
        userMocks();
        taskMocks();
        projectMocks();
    }

    @Test
    public void shouldCreateNewProject() throws Exception {

        Mockito.when(repositoryProject.findByIdAndActiveTrue(project.getId())).thenReturn(project);

        String requestJson = objectMapper.writeValueAsString(projectDTO);

        mockMvc.perform(post("/projects")
                        .with(httpBasic(basicUser.username(), basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        EntityProject newProject = repositoryProject.findByIdAndActiveTrue(project.getId());
        Assertions.assertEquals(project, newProject);
    }

    @Test
    public void shouldActivateProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(projectMock.getId())).thenReturn(projectMock);

        mockMvc.perform(put("/projects/activate/" + projectMock.getId())
                        .with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(projectMock, Mockito.times(1)).activate();
    }

    @Test
    public void shouldInactivateProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(projectMock.getId())).thenReturn(projectMock);

        mockMvc.perform(put("/projects/inactivate/" + projectMock.getId())
                        .with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(projectMock, Mockito.times(1)).inactivate();
    }

    @Test
    public void shouldDeleteTask() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);
        Mockito.when(repositoryTask.findAllByProjectIdAndActiveTrue(project.getId())).thenReturn(List.of(taskMock));

        mockMvc.perform(delete("/projects/" + project.getId())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());


        Mockito.verify(repositoryProject, Mockito.times(1)).deleteById(project.getId());
        Mockito.verify(repositoryTask, Mockito.times(0)).save(Mockito.any()); // -> precisa atender mais duas linhas no método 
    }
}
