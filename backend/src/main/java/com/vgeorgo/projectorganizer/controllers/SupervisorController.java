package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.exceptions.business.ResourceNotFoundException;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SupervisorController {
    @Autowired
    UserRepository repository;

    private User loadResource(Long id) {
        return repository.findByIdAndType(id, User.SUPERVISOR)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @GetMapping("/supervisors")
    public List<User> all() {
        return repository.findAllByType(User.SUPERVISOR);
    }

    @PostMapping("/supervisors")
    public User create(@Valid @RequestBody User user) {
        user.setAsSupervisor();
        return repository.save(user);
    }

    @GetMapping("/supervisors/{id}")
    public User find(@PathVariable(value = "id") Long id) {
        return loadResource(id);
    }

    @PutMapping("/supervisors/{id}")
    public User update(@PathVariable(value = "id") Long id, @Valid @RequestBody User user) {
        User updateUser = loadResource(id);

        updateUser.setName(user.getName());
        updateUser.setType(user.getType());
        updateUser.setAsSupervisor();

        updateUser = repository.save(updateUser);
        return updateUser;
    }

    @DeleteMapping("/supervisors/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        User user = loadResource(id);
        repository.delete(user);

        return ResponseEntity.ok().build();
    }
}
