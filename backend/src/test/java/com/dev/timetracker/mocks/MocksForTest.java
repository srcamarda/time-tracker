package com.dev.timetracker.mocks;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;


public class MocksForTest {

    public static DTOCreateUser userDTO;
    public static DTOCreateUser userDTO2;
    public static DTOLoginUser loginDTO;
    public static EntityUser user;
    public static EntityUser user2;
    public static EntityUser userMock;

    public static DTOCreateTask taskDTO;
    public static DTOCreateTask taskDTO2;
    public static EntityTask task;
    public static EntityTask task2;
    public static EntityTask taskMock;

    public static DTOCreateProject projectDTO;
    public static DTOCreateProject projectDTO2;
    public static EntityProject project;
    public static EntityProject project2;
    public static EntityProject projectMock;

    public static LocalDate startDate;
    public static LocalDate endDate;
    public static Timestamp startTimestamp;
    public static Timestamp endTimestamp;

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

    public static void taskMocks() {
        taskDTO = new DTOCreateTask(
                1L,
                "Creditos",
                "Corrigir creditos finais",
                Tag.URGENT,
                user,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().plusHours(2))
        );

        taskDTO2 = new DTOCreateTask(
                2L,
                "Legenda",
                "Finalizar legendas em PT",
                Tag.IMPORTANT,
                user2,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().plusHours(1))
        );

        task = new EntityTask(taskDTO);
        task2 = new EntityTask(taskDTO2);

        task.setId(1L);
        task2.setId(2L);

        task.setActive(true);
        task2.setActive(true);

        task.setProject(project);
        task2.setProject(project);

        taskMock = Mockito.mock(EntityTask.class);
        taskMock.setId(task.getId());
        taskMock.setTitle(task.getTitle());
        taskMock.setDescription(task.getDescription());
    }

    public static void projectMocks() {
        userMocks();
        taskMocks();

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
                "O inimigo agora é outro",
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

    public static void initializeVariables() {
        startDate = LocalDate.of(2021, 5, 1);
        endDate = LocalDate.of(2021, 5, 2);
        startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));
    }

}