package com.dev.timetracker.entity;

import com.dev.timetracker.dto.project.DTOCreateProject;
import com.dev.timetracker.dto.project.DTOUpdateProject;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.utility.category.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Table(name = "projects")
@Entity(name = "Project")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EntityProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private Boolean active;

    @ManyToMany
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "id_project", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"))
    private Set<EntityUser> users;

    @ManyToMany
    @JoinTable(name = "project_tasks",
            joinColumns = @JoinColumn(name = "id_project", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_task", referencedColumnName = "id"))
    private Set<EntityTask> tasks;

    @ElementCollection
    @CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "id_project"))
    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    private Set<Tag> tags;

    public EntityProject(DTOCreateProject data) {
        this.active = true;
        this.title = data.title();
        this.description = data.description();
        this.startTime = data.startTime();
        this.endTime = data.endTime();
        this.users = data.users();
        this.tasks = data.tasks();
        this.tags = data.tags();
    }

    public void update(DTOUpdateProject data) {
        if (data.title() != null) {
            this.title = data.title();
        }
        if (data.description() != null) {
            this.description = data.description();
        }
        if (data.startTime() != null) {
            this.startTime = data.startTime();
        }
        if (data.endTime() != null) {
            this.endTime = data.endTime();
        }
    }

    public void activate() {
        this.active = true;
    }

    public void inactivate() {
        this.active = false;
    }
}