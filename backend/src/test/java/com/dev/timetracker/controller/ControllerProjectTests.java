package com.dev.timetracker.controller;

import com.dev.timetracker.dto.project.DTOListProject;
import com.dev.timetracker.dto.project.DTOUpdateProject;
import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.service.ReportService;
import com.dev.timetracker.utility.category.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.parser.EntityTag;
import org.junit.jupiter.api.*;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

import static com.dev.timetracker.mocks.ProjectMocks.*;
import static com.dev.timetracker.mocks.TaskMocks.*;
import static com.dev.timetracker.mocks.UserMocks.*;
import static com.dev.timetracker.security.SecurityConfigTest.basicUser;
import static com.dev.timetracker.security.SecurityConfigTest.mockLogin;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
    public void createShouldCreateNewProject() throws Exception {

        Mockito.when(repositoryProject.findByIdAndActiveTrue(project.getId())).thenReturn(project);

        String requestJson = objectMapper.writeValueAsString(projectDTO);

        mockMvc.perform(post("/projects")
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        EntityProject newProject = repositoryProject.findByIdAndActiveTrue(project.getId());
        Assertions.assertEquals(project, newProject);
    }

    @Test
    public void getShouldReturnProject() throws Exception {

        Mockito.when(repositoryProject.findByIdAndActiveTrue(project.getId())).thenReturn(project);

        String responseJson = objectMapper.writeValueAsString(new DTOListProject(project));

        mockMvc.perform(get("/projects/" + project.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listShouldReturnProjectByUserId() throws Exception {

        List<EntityProject> projects = Arrays.asList(project, project2);

        Mockito.when(repositoryProject.findByUsersId(user.getId())).thenReturn(projects);

        String responseJson = objectMapper.writeValueAsString(projects.stream().map(DTOListProject::new).collect(Collectors.toList()));

        mockMvc.perform(get("/projects/users/" + user.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listShouldReturnProject() throws Exception {

        Page<EntityProject> projects = new PageImpl<>(List.of(project, project2));

        Mockito.when(repositoryProject.findAllByActiveTrue(Mockito.any(Pageable.class))).thenReturn(projects);

        String responseJson = objectMapper.writeValueAsString(projects.map(DTOListProject::new).getContent());

        mockMvc.perform(get("/projects")
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listAllShouldReturnProjects() throws Exception {

        Page<EntityProject> projects = new PageImpl<>(List.of(project, project2));

        Mockito.when(repositoryProject.findAllByActiveTrue(Mockito.any(Pageable.class))).thenReturn(projects);

        String responseJson = objectMapper.writeValueAsString(projects.map(DTOListProject::new).getContent());

        mockMvc.perform(get("/projects/all")
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listUsersShouldReturnUsersInProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);

        mockMvc.perform(get("/projects/" + project.getId() + "/users")
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());
    }

    @Test
    public void listTasksShouldReturnTasksInProject() throws Exception {

        List<EntityTask> tasks = Arrays.asList(task, task2);
        List<DTOListTask> dtoTasks = tasks.stream().map(DTOListTask::new).toList();

        Mockito.when(repositoryTask.findByProjectIdAndActiveTrue(project.getId())).thenReturn(tasks);

        String responseJson = objectMapper.writeValueAsString(dtoTasks);

        mockMvc.perform(get("/projects/" + project.getId() + "/tasks")
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listTasksShouldReturnNoContent() throws Exception {

        Mockito.when(repositoryTask.findByProjectIdAndActiveTrue(project.getId())).thenReturn(List.of());

        mockMvc.perform(get("/projects/" + project.getId() + "/tasks")
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getRakingShouldReturnRanking() throws Exception {

        DTOUserTime userTime = new DTOUserTime("joao", 10L);
        List<DTOUserTime> ranking = List.of(userTime);

        Mockito.when(reportService.calculateUserTimeRankingForProject(project.getId())).thenReturn(ranking);

        String responseJson = objectMapper.writeValueAsString(ranking);

        mockMvc.perform(get("/projects/" + project.getId() + "/users/report")
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void updateShouldUpdateProject() throws Exception {

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
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserShouldAddUserToProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);
        Mockito.when(repositoryUser.getReferenceById(user.getId())).thenReturn(userMock);

        mockMvc.perform(put("/projects/" + project.getId() + "/users/" + user.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(projectMock, Mockito.times(1)).setUsers(Mockito.any());
        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void addTaskShouldAddTaskToProject() throws Exception {

        Mockito.when(repositoryProject.findById(project.getId())).thenReturn(Optional.ofNullable(projectMock));
        Mockito.when(repositoryTask.findById(task.getId())).thenReturn(Optional.ofNullable(taskMock));

        mockMvc.perform(put("/projects/" + project.getId() + "/tasks/" + task.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(repositoryTask, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void addTagShouldAddTagToProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);

        mockMvc.perform(put("/projects/" + project.getId() + "/tags")
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .param("tag", Tag.IMPORTANT.toString()))
                .andExpect(status().isOk());

        Mockito.verify(projectMock, Mockito.times(1)).setTags(Mockito.any());
        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void activateShouldActivateProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(projectMock.getId())).thenReturn(projectMock);

        mockMvc.perform(put("/projects/activate/" + projectMock.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(projectMock, Mockito.times(1)).activate();
    }

    @Test
    public void inactivateShouldInactivateProject() throws Exception {

        Mockito.when(repositoryProject.getReferenceById(projectMock.getId())).thenReturn(projectMock);

        mockMvc.perform(put("/projects/inactivate/" + projectMock.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(projectMock, Mockito.times(1)).inactivate();
    }

    @Test
    public void deleteShouldDeleteProject() throws Exception {

        Set<EntityTask> tasks = new HashSet<>(List.of(taskMock));

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);
        Mockito.when(repositoryTask.findAllByProjectIdAndActiveTrue(project.getId())).thenReturn(List.of(taskMock));
        Mockito.when(projectMock.getTasks()).thenReturn(tasks);

        mockMvc.perform(delete("/projects/" + project.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(repositoryProject, Mockito.times(1)).deleteById(project.getId());
        Mockito.verify(repositoryTask, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void deleteUserShouldDeleteUserFromProject() throws Exception {

        Set<EntityUser> users = new HashSet<>(List.of(userMock));

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);
        Mockito.when(repositoryUser.getReferenceById(user.getId())).thenReturn(userMock);
        Mockito.when(projectMock.getUsers()).thenReturn(users);

        mockMvc.perform(delete("/projects/" + project.getId() + "/users/" + user.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void deleteTaskShouldDeleteTaskFromProject() throws Exception {

        Set<EntityTask> tasks = new HashSet<>(List.of(taskMock));

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);
        Mockito.when(repositoryTask.getReferenceById(task.getId())).thenReturn(taskMock);
        Mockito.when(projectMock.getTasks()).thenReturn(tasks);

        mockMvc.perform(delete("/projects/" + project.getId() + "/tasks/" + task.getId())
                        .with(user(basicUser.username()).password(basicUser.cpf())))
                .andExpect(status().isOk());

        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void deleteTagShouldDeleteTagFromProject() throws Exception {

        Set<Tag> tags = new HashSet<>(List.of(Tag.IMPORTANT, Tag.IN_PROGRESS));

        Mockito.when(repositoryProject.getReferenceById(project.getId())).thenReturn(projectMock);
        Mockito.when(projectMock.getTags()).thenReturn(tags);

        mockMvc.perform(delete("/projects/" + project.getId() + "/tags")
                        .with(user(basicUser.username()).password(basicUser.cpf()))
                        .contentType(APPLICATION_JSON)
                        .param("tag", Tag.IMPORTANT.toString()))
                .andExpect(status().isOk());

        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }
}