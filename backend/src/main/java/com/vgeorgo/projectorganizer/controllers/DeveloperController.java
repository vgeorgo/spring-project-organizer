package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.exceptions.business.BadRequestException;
import com.vgeorgo.projectorganizer.exceptions.business.ResourceNotFoundException;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
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

    /**
     * Handles validation of supervisor when creating/updating
     * @param loaded Entity being updated
     * @param update Entity on the request body
     */
    private void handleSupervisor(User loaded, User update) {
        // Sets the supervisor if the parameter exists
        if(update.getSupervisor() != null) {
            // Blocks supervisor with the same id
            if(loaded.getId() != null && loaded.getId().equals(update.getSupervisor().getId())) {
                throw new BadRequestException("User can not be his own supervisor", "supervisor", update.getSupervisor().getId());
            }

            // Validates if the user really is a supervisor
            User supervisor = repository.findByIdAndType(update.getSupervisor().getId(), User.SUPERVISOR)
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor", "id", update.getSupervisor().getId()));
            loaded.setSupervisor(supervisor);
        }
    }

    @GetMapping("/developers")
    public List<User> all() {
        return repository.findAllByType(User.DEVELOPER);
    }

    @PostMapping("/developers")
    public User create(@Valid @RequestBody User user) {
        user.setAsDeveloper();
        handleSupervisor(user, user);
        return repository.save(user);
    }

    @GetMapping("/developers/{id}")
    public User find(@PathVariable(value = "id") Long id) {
        return loadResource(id);
    }

    @PutMapping("/developers/{id}")
    public User update(@PathVariable(value = "id") Long id, @Valid @RequestBody User user) {
        User updateUser = loadResource(id);
        updateUser.setName(user.getName());
        updateUser.setType(user.getType());
        updateUser.setAsDeveloper();

        handleSupervisor(updateUser, user);

        updateUser = repository.save(updateUser);
        return repository.refresh(updateUser);
    }

    @DeleteMapping("/developers/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        User user = loadResource(id);
        repository.delete(user);

        return ResponseEntity.ok().build();
    }
}
