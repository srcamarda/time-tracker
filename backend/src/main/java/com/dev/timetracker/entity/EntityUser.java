package com.dev.timetracker.entity;

import com.dev.timetracker.dto.user.DTOUpdateUser;

import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.utility.AddressAPI;
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
    private String addrZip;
    private String addrCountry;
    private String addrState;
    private String addrCity;
    private String addrStreet;
    private String addrDistrict;
    private Integer addrNumber;
    private Boolean active;

    public EntityUser(DTOCreateUser data) {
        this.active = true;
        this.username = data.username();
        this.name = data.name();
        this.cpf = data.cpf();
        this.email = data.email();
        this.role = data.role();

        if (data.addrZip() != null) {
            AddressAPI addressAPI = new AddressAPI(data.addrZip());
            this.addrZip = data.addrZip();
            this.addrCountry = addressAPI.getAddrCountry();
            this.addrState = addressAPI.getAddrState();
            this.addrCity = addressAPI.getAddrCity();
            this.addrStreet = addressAPI.getAddrStreet();
            this.addrDistrict = addressAPI.getAddrDistrict();
        }

        this.addrNumber = data.addrNumber();
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
        if (data.addrZip() != null) {
            this.addrZip = data.addrZip();
        }
        if (data.addrCountry() != null) {
            this.addrCountry = data.addrCountry();
        }
        if (data.addrState() != null) {
            this.addrState = data.addrState();
        }
        if (data.addrCity() != null) {
            this.addrCity = data.addrCity();
        }
        if (data.addrStreet() != null) {
            this.addrStreet = data.addrStreet();
        }
        if (data.addrDistrict() != null) {
            this.addrDistrict = data.addrDistrict();
        }
        if (data.addrNumber() != null) {
            this.addrNumber = data.addrNumber();
        }
    }

    public void activate() {
        this.active = true;
    }

    public void inactivate() {
        this.active = false;
    }
}