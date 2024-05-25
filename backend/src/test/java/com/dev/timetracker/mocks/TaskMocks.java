package com.dev.timetracker.mocks;

import com.dev.timetracker.dto.task.DTOCreateTask;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.utility.category.Tag;

import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.dev.timetracker.mocks.ProjectMocks.project;
import static com.dev.timetracker.mocks.ProjectMocks.project2;
import static com.dev.timetracker.mocks.UserMocks.user;
import static com.dev.timetracker.mocks.UserMocks.user2;


public class TaskMocks {

    public static DTOCreateTask taskDTO;
    public static DTOCreateTask taskDTO2;
    public static EntityTask task;
    public static EntityTask task2;
    public static EntityTask taskMock;

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
        task2.setProject(project2);

        taskMock = Mockito.mock(EntityTask.class);
        taskMock.setId(task.getId());
        taskMock.setTitle(task.getTitle());
        taskMock.setDescription(task.getDescription());
    }


}