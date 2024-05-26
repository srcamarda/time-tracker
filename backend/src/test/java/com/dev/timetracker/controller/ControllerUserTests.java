package com.dev.timetracker.controller;

import com.dev.timetracker.dto.report.DTOAverageTime;
import com.dev.timetracker.dto.report.DTOProjectTime;
import com.dev.timetracker.dto.report.DTOTimeWork;
import com.dev.timetracker.dto.task.DTOListTask;
import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.dto.user.DTOLoginUser;
import com.dev.timetracker.dto.user.DTOUpdateUser;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryProject;
import com.dev.timetracker.repository.RepositoryTask;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.security.SecurityConfig;
import com.dev.timetracker.service.ReportService;
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
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.dev.timetracker.mocks.ProjectMocks.*;
import static com.dev.timetracker.mocks.TaskMocks.*;
import static com.dev.timetracker.mocks.UserMocks.*;
import static com.dev.timetracker.mocks.VariablesMocks.*;
import static com.dev.timetracker.security.SecurityConfigTest.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ControllerUserTests {

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

    @InjectMocks
    private ControllerUser controllerUser;

    @MockBean
    private SecurityConfig securityConfig;

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeAll
    public static void initializeTestMocks() {
        userMocks();
        taskMocks();
        projectMocks();
        initializeVariables();
    }

//    @BeforeEach
//    public void loginMock() {
//        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mockLogin);
//    }

    @Test
    public void registerShouldCreateNewUser() throws Exception {

        Mockito.when(repositoryUser.existsByUsername(userDTO.username())).thenReturn(false);
        Mockito.when(repositoryUser.existsByCpf(userDTO.cpf())).thenReturn(false);

        String requestJson = objectMapper.writeValueAsString(userDTO);

        //When register is successful, response should be ok
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        Mockito.when(repositoryUser.findByUsernameAndActiveTrue(userDTO.username())).thenReturn(user);

        EntityUser newUser = repositoryUser.findByUsernameAndActiveTrue(userDTO.username());

        Assertions.assertEquals(user, newUser);
    }

    @Test
    public void registerShouldReturnBadRequestWhenUsernameAlreadyExists() throws Exception {

        Mockito.when(repositoryUser.existsByUsername(userDTO.username())).thenReturn(true);

        String requestJson = objectMapper.writeValueAsString(userDTO);

        //When username is already registered, a bad request should be returned
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerShouldReturnBadRequestWhenCpfAlreadyExists() throws Exception {

        Mockito.when(repositoryUser.existsByCpf(userDTO.cpf())).thenReturn(true);

        String requestJson = objectMapper.writeValueAsString(userDTO);

        //When cpf is already registered, a bad request should be returned
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginShouldReturnOkLoginData() throws Exception {

        Mockito.when(repositoryUser.findByUsernameAndActiveTrue(loginDTO.username())).thenReturn(user);

        String requestJson = objectMapper.writeValueAsString(loginDTO);

        //When login is successful, the username and cpf should be returned
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(loginDTO)));
    }

    @Test
    public void loginShouldReturnNotFoundWhenUserNotFound() throws Exception {

        Mockito.when(repositoryUser.findByUsernameAndActiveTrue(loginDTO.username())).thenReturn(null);

        String requestJson = objectMapper.writeValueAsString(loginDTO);

        //When user is not found, a not found should be returned
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(containsString("User not found")));
    }

    @Test
    public void loginShouldReturnBadRequestWhenCpfIncorrect() throws Exception {

        DTOLoginUser loginDTO2 = new DTOLoginUser(
                userDTO.username(),
                "63311986016"
        );

        Mockito.when(repositoryUser.findByUsernameAndActiveTrue(userDTO.username())).thenReturn(user);

        String requestJson = objectMapper.writeValueAsString(loginDTO2);

        //When cpf is incorrect, a bad request should be returned
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Incorrect cpf")));
    }

    @Test
    public void getShouldReturnUser() throws Exception {

        Mockito.when(repositoryUser.findByIdAndActiveTrue(user.getId())).thenReturn(user);

        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mockLogin);

        String responseJson = objectMapper.writeValueAsString(new DTOListUser(user));

        //When user is found, it should be returned
        mockMvc.perform(get("/users/" + user.getId())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(testUser.username(), testUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void listAndListAllShouldReturnUsers() throws Exception {

        Page<EntityUser> users = new PageImpl<>(List.of(user, user2));

        Mockito.when(repositoryUser.findAllByActiveTrue(Mockito.any(Pageable.class))).thenReturn(users);

        String responseJson = objectMapper.writeValueAsString(users.map(DTOListUser::new).getContent());

        //When users are found, they should be returned
        mockMvc.perform(get("/users")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        mockMvc.perform(get("/users/all")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void updateShouldUpdateUser() throws Exception {

        DTOUpdateUser updateDTO = new DTOUpdateUser(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getAddrZip(),
                user.getAddrCountry(),
                user.getAddrState(),
                user.getAddrCity(),
                user.getAddrStreet(),
                user.getAddrDistrict(),
                user.getAddrNumber()
        );

        Mockito.when(repositoryUser.getReferenceById(user.getId())).thenReturn(userMock);

        String responseJson = objectMapper.writeValueAsString(updateDTO);

        //When user is updated, it should return ok
        mockMvc.perform(put("/users")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseJson))
                .andExpect(status().isOk());

        //Update user must be called 1 time
        Mockito.verify(userMock, Mockito.times(1)).update(updateDTO);
    }

    @Test
    public void activateShouldActivateUser() throws Exception {

        Mockito.when(repositoryUser.getReferenceById(userMock.getId())).thenReturn(userMock);

        //When user is activated, it should return ok
        mockMvc.perform(put("/users/activate/" + userMock.getId())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());

        //Activate user must be called 1 time
        Mockito.verify(userMock, Mockito.times(1)).activate();
    }

    @Test
    public void inactivateShouldInactivateUser() throws Exception {

        Mockito.when(repositoryUser.getReferenceById(userMock.getId())).thenReturn(userMock);

        //When user is inactivated, it should return ok
        mockMvc.perform(put("/users/inactivate/" + userMock.getId())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());

        //Inactivate user must be called 1 time
        Mockito.verify(userMock, Mockito.times(1)).inactivate();
    }

    @Test
    public void deleteShouldDeleteUser() throws Exception {

        Mockito.when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
        Mockito.when(repositoryTask.findAllByIdUser(user)).thenReturn(List.of(taskMock));
        Mockito.when(repositoryProject.findByUsersId(user.getId())).thenReturn(List.of(projectMock));

        //When user is deleted, it should return ok
        mockMvc.perform(delete("/users/" + user.getId())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk());

        //Check functions that must be called
        Mockito.verify(repositoryUser, Mockito.times(1)).deleteById(user.getId());
        Mockito.verify(repositoryTask, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(repositoryProject, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void listTasksShouldReturnTasks() throws Exception {

        Page<EntityTask> tasks = new PageImpl<>(List.of(task, task2));

        Mockito.when(repositoryUser.getReferenceById(user.getId())).thenReturn(user);
        Mockito.when(repositoryTask.findAllByIdUserAndActiveTrue(eq(user), Mockito.any(Pageable.class))).thenReturn(tasks);

        String responseJson = objectMapper.writeValueAsString(tasks.map(DTOListTask::new).getContent());

        //When tasks are found for the user, they should be returned
        mockMvc.perform(get("/users/" + user.getId() + "/tasks")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void getWorkHoursShouldReturnWorkHours() throws Exception {


        DTOProjectTime projectTime = new DTOProjectTime("Moana", 100L);

        Mockito.when(reportService.hoursWorkedByUserAllProjects(user.getId())).thenReturn(List.of(projectTime));

        String responseJson = objectMapper.writeValueAsString(List.of(projectTime));

        //Should return the hours worked by the user
        mockMvc.perform(get("/users/" + user.getId() + "/hours")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void getAverageTimeShouldReturnAverageTime() throws Exception {

        DTOAverageTime averageTime = new DTOAverageTime(2L, 20L, 10.0);

        Mockito.when(reportService.hoursWorkedByUserAverageTime(user.getId())).thenReturn(averageTime);

        String responseJson = objectMapper.writeValueAsString(averageTime);

        //Should return the average time worked by the user
        mockMvc.perform(get("/users/" + user.getId() + "/average-task-time")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void getWorkReportShouldReturnWorkReport() throws Exception {

        DTOTimeWork timeWork = new DTOTimeWork("Moana", "Creditos", 32L);
        DTOTimeWork timeWork2 = new DTOTimeWork("Moana", "Legenda", 28L);

        Mockito.when(reportService.calculateWorkForUser(user.getId(), startDate, endDate)).thenReturn(List.of(timeWork, timeWork2));

        String responseJson = objectMapper.writeValueAsString(List.of(timeWork, timeWork2));

        //Should return the hours worked by the user in tasks
        mockMvc.perform(get("/users/" + user.getId() + "/work-report")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void getWorkReportShouldReturnNoContent() throws Exception {

        Mockito.when(reportService.calculateWorkForUser(user.getId(), startDate, endDate)).thenReturn(List.of());

        //Should return no content if no time is found
        mockMvc.perform(get("/users/" + user.getId() + "/work-report")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(basicUser.username(), basicUser.cpf())))
                .andExpect(status().isNoContent());
    }
}