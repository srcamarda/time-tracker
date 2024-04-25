package com.dev.timetracker.controller;

import com.dev.timetracker.dto.user.DTOListUser;
import com.dev.timetracker.dto.user.DTOCreateUser;
import com.dev.timetracker.dto.user.DTOUpdateUser;
import com.dev.timetracker.entity.EntityUser;
import com.dev.timetracker.repository.RepositoryUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private RepositoryUser userRepository;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid DTOCreateUser data) {
        userRepository.save(new EntityUser(data));
    }

    @GetMapping("{id}")
    public DTOListUser get(@PathVariable Long id) {
        return new DTOListUser(userRepository.getReferenceById(id));
    }

    @GetMapping
    public List<DTOListUser> list(@PageableDefault(size = 5, sort ={"name"}) Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable).map(DTOListUser::new).getContent();
    }
    @GetMapping("all")
    public List<DTOListUser> listAll(@PageableDefault( sort ={"name"}) Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable).map(DTOListUser::new).getContent();
    }
    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DTOUpdateUser data){
        var user = userRepository.getReferenceById(data.id());
        user.update(data);
    }
    @PutMapping("/activate/{id}")
    @Transactional
    public void activate(@PathVariable Long id){
        var user = userRepository.getReferenceById(id);
        user.activate();
    }
    @PutMapping("/inactivate/{id}")
    @Transactional
    public void inactivate(@PathVariable Long id){
        var user = userRepository.getReferenceById(id);
        user.inactivate();
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id){
        userRepository.deleteById(id);
    }
}
