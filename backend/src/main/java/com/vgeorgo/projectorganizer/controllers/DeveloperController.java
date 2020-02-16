package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.exceptions.business.ResourceNotFoundException;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeveloperController {
    @Autowired
    UserRepository repository;

    private User loadResource(Long id) {
        return repository.findByIdAndType(id, User.DEVELOPER)
                .orElseThrow(() -> new ResourceNotFoundException("Developer", "id", id));
    }

    @GetMapping("/developers")
    public List<User> all() {
        return repository.findAllByType(User.DEVELOPER);
    }

    @GetMapping("/developers/{id}")
    public User find(@PathVariable(value = "id") Long id) {
        return loadResource(id);
    }
}
