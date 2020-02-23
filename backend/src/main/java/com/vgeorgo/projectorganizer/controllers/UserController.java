package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.exceptions.business.InvalidSupervisorException;
import com.vgeorgo.projectorganizer.exceptions.business.ResourceNotFoundException;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import com.vgeorgo.projectorganizer.validators.user.SupervisorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository repository;

    private User loadResource(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User find(@PathVariable(value = "id") Long id) {
        return loadResource(id);
    }

    @PostMapping("/users")
    public User store(@Valid @RequestBody User user) {
        if(!SupervisorValidator.isValid(user.getSupervisor()))
            throw new InvalidSupervisorException(
                    "supervisor",
                    user.getSupervisor() != null ? user.getSupervisor().getId() : null);

        return repository.save(user);
    }

    @PutMapping("/users/{id}")
    public User update(@PathVariable(value = "id") Long id, @Valid @RequestBody User user) {
        if(!SupervisorValidator.isValid(user.getSupervisor(), id))
            throw new InvalidSupervisorException("supervisor", user.getSupervisor().getId());

        User updateUser = loadResource(id);
        updateUser.setName(user.getName());
        updateUser.setType(user.getType());
        if(updateUser.getType().equals(User.DEVELOPER))
            updateUser.setSubordinates(new ArrayList<>());

        // Relations
        updateUser.setSupervisor(user.getSupervisor());

        updateUser = repository.save(updateUser);
        return repository.refresh(updateUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        User user = loadResource(id);

        repository.delete(user);
        return ResponseEntity.ok().build();
    }
}