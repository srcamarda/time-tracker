package com.dev.timetracker.controller;

import com.dev.timetracker.dto.DTOListUser;
import com.dev.timetracker.dto.DTORegisterUser;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public Page<DTOListUser> listar(@PageableDefault(size = 10, sort ={"name"}) Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable).map(DTOListUser::new);
    }
}
