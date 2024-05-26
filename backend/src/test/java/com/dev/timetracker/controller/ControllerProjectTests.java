package com.dev.timetracker.controller;

import com.dev.timetracker.dto.project.DTOListProject;
import com.dev.timetracker.dto.project.DTOUpdateProject;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dev.timetracker.mocks.ProjectMocks.*;
import static com.dev.timetracker.mocks.TaskMocks.*;
import static com.dev.timetracker.mocks.TaskMocks.task;
import static com.dev.timetracker.mocks.UserMocks.user;
import static com.dev.timetracker.mocks.UserMocks.userMocks;
import static com.dev.timetracker.security.SecurityConfigTest.basicUser;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    public void shouldReturnProject() throws Exception {

        Mockito.when(repositoryProject.findByIdAndActiveTrue(project.getId())).thenReturn(project);

        String responseJson = objectMapper.writeValueAsString(new DTOListProject(project));

        mockMvc.perform(get("/projects/" + project.getId())
                        .with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

    }

    @Test
    public void shouldReturnProjectByUserId() throws Exception {

        List<EntityProject> projects = Arrays.asList(project, project2);

        Mockito.when(repositoryProject.findByUsersId(user.getId())).thenReturn(projects);

        String responseJson = objectMapper.writeValueAsString(projects.stream().map(DTOListProject::new).collect(Collectors.toList()));

        mockMvc.perform(get("/projects/users/" + user.getId()).
                        with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

    }

    @Test
    public void shouldlistReturnProject() throws Exception {

        Page<EntityProject> projects = new PageImpl<>(List.of(project, project2));

        Mockito.when(repositoryProject.findAllByActiveTrue(Mockito.any(Pageable.class))).thenReturn(projects);

        String responseJson = objectMapper.writeValueAsString(projects.map(DTOListProject::new).getContent());

        mockMvc.perform(get("/projects").
                        with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void shouldlistAllReturnProjects() throws Exception {

        Page<EntityProject> projects = new PageImpl<>(List.of(project, project2));

        Mockito.when(repositoryProject.findAllByActiveTrue(Mockito.any(Pageable.class))).thenReturn(projects);

        String responseJson = objectMapper.writeValueAsString(projects.map(DTOListProject::new).getContent());

        mockMvc.perform(get("/projects/all").
                        with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void shouldReturnListUsersInProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);

        mockMvc.perform(get("/projects/" + project.getId() +"/users").
                        with(httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());
    }

//    @Test
//    public void shouldReturnListTasksInProject() throws Exception {
//
//        List<EntityTask> tasks = Arrays.asList(task, task2);
//
//        Mockito.when(repositoryTask.findByProjectIdAndActiveTrue(project.getId())).thenReturn(tasks);
//
//        String responseJson = new ObjectMapper().writeValueAsString(tasks.stream().map(DTOListTask::new).collect(Collectors.toList()));
//
//
//        mockMvc.perform(get("/projects/" + project.getId() +"/tasks")
//                        .with(httpBasic(basicUser.username(), basicUser.cpf()))
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(responseJson));
//    }

    @Test
    public void shouldReturnListTasksNoContentInProject() throws Exception {

        Mockito.when(repositoryTask.findByProjectIdAndActiveTrue(project.getId())).thenReturn(Arrays.asList());

        mockMvc.perform(get("/projects/"+ project.getId() +"/tasks")
                        .with(httpBasic(basicUser.username(), basicUser.cpf()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateProject() throws Exception {

        DTOUpdateProject updateProject = new DTOUpdateProject(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStartTime(),
                project.getStartTime()
        );

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);

        String requestJson = objectMapper.writeValueAsString(updateProject);

        mockMvc.perform(put("/projects")
                        .with(httpBasic(basicUser.username(), basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

//        Mockito.verify(projectMock, Mockito.times(1)).update(updateProject);
    }

//    @Test
//    public void shouldAddUserToProject() throws Exception {
//
//        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(project);
//        Mockito.when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
//
//        mockMvc.perform(put("/projects/" + project.getId() + "/users/" + user.getId())
//                        .with(httpBasic(basicUser.username(), basicUser.cpf()))
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

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
