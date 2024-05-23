package com.dev.timetracker.controller;

import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOLoginUser;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryUser;
import com.dev.timetracker.utility.category.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @InjectMocks
    private ControllerUser controllerUser;

    static DTOCreateUser userDTO;
    static DTOLoginUser loginDTO;
    static EntityUser user;

    @BeforeAll
    public static void startTestUser() {

        userDTO = new DTOCreateUser(
                null,
                "tsilveira",
                "Thiago Silveira",
                "tsilveira@hotmail.com",
                "16213093710",
                Role.JUNIOR,
                "29560000",
                null,
                null,
                null,
                null,
                null,
                null);

        loginDTO = new DTOLoginUser(
                userDTO.username(),
                userDTO.cpf()
        );

        user = new EntityUser(userDTO);
    }

    @Test
    public void registerShouldCreateNewUser() throws Exception {

        String requestJson = objectMapper.writeValueAsString(userDTO);

        Mockito.when(repositoryUser.existsByUsername(userDTO.username())).thenReturn(false);
        Mockito.when(repositoryUser.existsByCpf(userDTO.cpf())).thenReturn(false);

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
                .andExpect(status().isNotFound());
    }

    @Test
    public void loginShouldReturnBadRequestWhenCpfIncorrect() throws Exception {

        DTOLoginUser loginDTO2 = new DTOLoginUser(
                userDTO.username(),
                "16213093711"
        );

        Mockito.when(repositoryUser.findByUsernameAndActiveTrue(loginDTO.username())).thenReturn(user);

        String requestJson = objectMapper.writeValueAsString(loginDTO2);

        //When cpf is incorrect, a bad request should be returned
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}