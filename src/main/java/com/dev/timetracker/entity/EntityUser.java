package com.dev.timetracker.entity;

import com.dev.timetracker.dto.DTORegisterUser;
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

    public EntityUser(DTORegisterUser data) {
        this.active = true;
        this.username = data.username();
        this.name = data.name();
        this.cpf = data.cpf();
        this.plan = data.plan();
        this.role = data.role();
        }
}



