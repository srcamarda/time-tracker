package com.dev.timetracker.mocks;

import com.dev.timetracker.dto.project.DTOCreateProject;
import com.dev.timetracker.entity.EntityProject;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.utility.category.Tag;

import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import static com.dev.timetracker.mocks.TaskMocks.*;
import static com.dev.timetracker.mocks.UserMocks.*;


public class ProjectMocks {

    public static DTOCreateProject projectDTO;
    public static DTOCreateProject projectDTO2;
    public static EntityProject project;
    public static EntityProject project2;
    public static EntityProject projectMock;

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
}