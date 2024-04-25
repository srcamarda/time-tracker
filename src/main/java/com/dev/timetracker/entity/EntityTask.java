package com.dev.timetracker.entity;

import com.dev.timetracker.dto.task.DTOCreateTask;
import com.dev.timetracker.dto.task.DTOUpdateTask;
import com.dev.timetracker.utility.category.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "tasks")
@Entity(name = "Task")
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
    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private EntityUser id_user;
    private Timestamp start_time;
    private Timestamp end_time;
    private Boolean active;

    public EntityTask(DTOCreateTask data) {
        this.active = true;
        this.title = data.title();
        this.description = data.description();
        this.tag = data.tag();
        this.id_user = data.id_user();
        this.start_time = data.start_time();
        this.end_time = data.end_time();
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
        if (data.id_user() != null) {
            this.id_user = data.id_user();
        }
        if (data.start_time() != null) {
            this.start_time = data.start_time();
        }
        if (data.end_time() != null) {
            this.end_time = data.end_time();
        }
    }

    public void activate() {
        this.active = true;
    }

    public void inactivate() {
        this.active = false;
    }
}