package com.dev.timetracker.entity;

import com.dev.timetracker.dto.user.DTOUpdateUser;

import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.utility.category.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EntityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String cpf;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String addr_zip;
    private String addr_country;
    private String addr_state;
    private String addr_city;
    private String addr_street;
    private Integer addr_number;
    private Boolean active;

    public EntityUser(DTOCreateUser data) {
        this.active = true;
        this.username = data.username();
        this.name = data.name();
        this.cpf = data.cpf();
        this.email = data.email();
        this.role = data.role();
        this.addr_zip = data.addr_zip();
        this.addr_country = data.addr_country();
        this.addr_state = data.addr_state();
        this.addr_city = data.addr_city();
        this.addr_street = data.addr_street();
        this.addr_number = data.addr_number();
    }

    public void update(DTOUpdateUser data) {
        if (data.username() != null) {
            this.username = data.username();
        }
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.email() != null) {
            this.email = data.email();
        }
        if (data.role() != null) {
            this.role = data.role();
        }
        if (data.addr_zip() != null) {
            this.addr_zip = data.addr_zip();
        }
        if (data.addr_country() != null) {
            this.addr_country = data.addr_country();
        }
        if (data.addr_state() != null) {
            this.addr_state = data.addr_state();
        }
        if (data.addr_city() != null) {
            this.addr_city = data.addr_city();
        }
        if (data.addr_street() != null) {
            this.addr_street = data.addr_street();
        }
        if (data.addr_number() != null) {
            this.addr_number = data.addr_number();
        }
    }

    public void activate() {
        this.active = true;
    }

    public void inactivate() {
        this.active = false;
    }
}