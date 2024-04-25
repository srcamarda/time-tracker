package com.dev.timetracker.entity;
import com.dev.timetracker.dto.user.DTOUpdateUser;

import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.utility.category.Role;
import com.dev.timetracker.utility.category.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// import java.util.UUID;

@Table(name ="users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EntityUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Plan plan;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean active;

    public EntityUser(DTOCreateUser data) {
        this.active = true;
        this.username = data.username();
        this.name = data.name();
        this.cpf = data.cpf();
        this.plan = data.plan();
        this.role = data.role();
        }

    public void update(DTOUpdateUser data) {
        if(data.username()!= null) {
            this.username = data.username();
        }
        if(data.name()!= null) {
            this.name = data.name();
        }
        if(data.plan()!= null) {
            this.plan = data.plan();
        }
        if(data.role()!= null) {
            this.role = data.role();
        }
    }
    public void activate() {
        this.active = true;
    }
    public void inactivate() {
        this.active = false;
    }

}



