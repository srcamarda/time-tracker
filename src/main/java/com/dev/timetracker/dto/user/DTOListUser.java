package com.dev.timetracker.dto.user;

import com.dev.timetracker.utility.category.Plan;
import com.dev.timetracker.utility.category.Role;
import com.dev.timetracker.entity.EntityUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DTOListUser(

        Long id,
        @NotBlank
        String name,
        @NotNull
        Plan plan,
        @NotNull
        Role role) {

        public DTOListUser(EntityUser user){
                this(user.getId(),user.getName(),user.getPlan(),user.getRole());
        }
}
