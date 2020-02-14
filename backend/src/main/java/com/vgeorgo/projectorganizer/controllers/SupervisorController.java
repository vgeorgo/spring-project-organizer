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
                .orElseThrow(() -> new ResourceNotFoundException("Supervisor", "id", id));
    }

    @GetMapping("/supervisors")
    public List<User> all() {
        return repository.findAllByType(User.SUPERVISOR);
    }

    @GetMapping("/supervisors/{id}")
    public User find(@PathVariable(value = "id") Long id) {
        return loadResource(id);
    }
}
