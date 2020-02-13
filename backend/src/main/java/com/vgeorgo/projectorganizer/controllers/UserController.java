package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.exceptions.business.ResourceNotFoundException;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}