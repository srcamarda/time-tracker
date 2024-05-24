package com.dev.timetracker.controller;

import com.dev.timetracker.dto.task.DTOCreateTask;
import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOLoginUser;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Role;
import com.dev.timetracker.utility.category.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ControllerMocks {

    static DTOCreateUser userDTO;
    static DTOCreateUser userDTO2;
    static DTOLoginUser loginDTO;
    static DTOLoginUser loginDTO2;
    static EntityUser user;
    static EntityUser user2;
    static EntityUser userMock;

    static DTOCreateTask taskDTO;
    static EntityTask task;

    public static void userMocks() {

        userDTO = new DTOCreateUser(
                1L,
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

        userDTO2 = new DTOCreateUser(
                2L,
                "joao",
                "Joao Batista",
                "jbatista@gamil.com",
                "94309916040",
                Role.PLENO,
                "76825100",
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

        loginDTO2 = new DTOLoginUser(
                userDTO2.username(),
                userDTO2.cpf()
        );

        user = new EntityUser(userDTO);
        user2 = new EntityUser(userDTO2);

        user.setId(1L);
        user2.setId(2L);

        userMock = Mockito.mock(EntityUser.class);
        userMock.setId(user.getId());
        userMock.setUsername(user.getUsername());
        userMock.setCpf(user.getCpf());
    }

    @BeforeAll
    public static void taskMocks() {

        taskDTO = new DTOCreateTask(
                1L,
                "O Teste do Teste",
                "vamos testar em tudo",
                Tag.IN_PROGRESS,
                user,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        task = new EntityTask(taskDTO);
        task.setId(1L);
    }
}