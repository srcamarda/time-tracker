package com.dev.timetracker.dto;

import com.dev.timetracker.utility.Plan;
import com.dev.timetracker.utility.Role;

public record DTORegisterUser(Long id,
                              String username,
                              String name,
                              String cpf,
                              Plan plan,
                              Role role) {}
