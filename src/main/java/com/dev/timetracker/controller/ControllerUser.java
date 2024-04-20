package com.dev.timetracker.controller;

import com.dev.timetracker.dto.DTORegisterUser;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private RepositoryUser userRepository;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid DTORegisterUser data) {
        userRepository.save(new EntityUser(data));
    }

}
