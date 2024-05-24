package com.dev.timetracker.controller;

import com.dev.timetracker.dto.project.DTOCreateProject;
import com.dev.timetracker.dto.task.DTOCreateTask;
import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOLoginUser;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Role;
import com.dev.timetracker.utility.category.Tag;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

public class ControllerMocks {

    static DTOCreateUser userDTO;
    static DTOCreateUser userDTO2;
    static DTOLoginUser loginDTO;
    static EntityUser user;
    static EntityUser user2;
    static EntityUser userMock;

    static DTOCreateTask taskDTO;
    static DTOCreateTask taskDTO2;
    static EntityTask task;
    static EntityTask task2;
    static EntityTask taskMock;

    static DTOCreateProject projectDTO;
    static DTOCreateProject projectDTO2;
    static EntityProject project;
    static EntityProject project2;
    static EntityProject projectMock;

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

        user = new EntityUser(userDTO);
        user2 = new EntityUser(userDTO2);

        user.setId(1L);
        user2.setId(2L);

        userMock = Mockito.mock(EntityUser.class);
        userMock.setId(user.getId());
        userMock.setUsername(user.getUsername());
        userMock.setCpf(user.getCpf());
    }

    public static void taskMocks() {

        taskDTO = new DTOCreateTask(
                1L,
                "Creditos",
                "Corrigir creditos finais",
                Tag.URGENT,
                user,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        taskDTO2 = new DTOCreateTask(
                2L,
                "Legenda",
                "Finalizar legendas em PT",
                Tag.IMPORTANT,
                user,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        task = new EntityTask(taskDTO);
        task2 = new EntityTask(taskDTO2);

        task.setId(1L);
        task2.setId(2L);

        taskMock = Mockito.mock(EntityTask.class);
        taskMock.setId(task.getId());
        taskMock.setTitle(task.getTitle());
        taskMock.setDescription(task.getDescription());
    }

    public static void projectMocks() {

        Set<EntityUser> setUsers = Set.of(user, user2);
        Set<EntityTask> setTasks = Set.of(task, task2);
        Set<Tag> setTags = Set.of(Tag.IN_PROGRESS, Tag.IMPORTANT);

        projectDTO = new DTOCreateProject(
                1L,
                "Moana",
                "Moana o filme",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                setUsers,
                setTasks,
                setTags
        );

        projectDTO2 = new DTOCreateProject(
                2L,
                "Moana 2",
                "O inimigo agora e outro",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                setUsers,
                setTasks,
                setTags
        );

        project = new EntityProject(projectDTO);
        project2 = new EntityProject(projectDTO2);

        project.setId(1L);
        project2.setId(2L);

        projectMock = Mockito.mock(EntityProject.class);
        projectMock.setId(project.getId());
        projectMock.setTitle(project.getTitle());
        projectMock.setDescription(project.getDescription());
    }
}