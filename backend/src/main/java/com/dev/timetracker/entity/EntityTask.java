package com.dev.timetracker.entity;

import com.dev.timetracker.dto.task.DTOCreateTask;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.utility.category.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "tasks")
@Entity(name = "Task")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EntityTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Tag tag;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private EntityUser idUser;
    private Timestamp startTime;
    private Timestamp endTime;
    private Boolean active;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_project", referencedColumnName = "id")
    private EntityProject project;

    public EntityTask(DTOCreateTask data) {
        this.active = true;
        this.title = data.title();
        this.description = data.description();
        this.tag = data.tag();
        this.idUser = data.idUser();
        this.startTime = data.startTime();
        this.endTime = data.endTime();
    }

    public void update(DTOUpdateTask data) {
        if (data.title() != null) {
            this.title = data.title();
        }
        if (data.description() != null) {
            this.description = data.description();
        }
        if (data.tag() != null) {
            this.tag = data.tag();
        }
        if (data.idUser() != null) {
            this.idUser = data.idUser();
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