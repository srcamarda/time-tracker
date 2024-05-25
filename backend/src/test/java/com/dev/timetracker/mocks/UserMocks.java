package com.dev.timetracker.mocks;

import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOLoginUser;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Role;

import org.mockito.Mockito;

public class UserMocks {

    public static DTOCreateUser userDTO;
    public static DTOCreateUser userDTO2;
    public static DTOLoginUser loginDTO;
    public static EntityUser user;
    public static EntityUser user2;
    public static EntityUser userMock;

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
                null
        );

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
                null
        );

        loginDTO = new DTOLoginUser(
                userDTO.username(),
                userDTO.cpf()
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
}