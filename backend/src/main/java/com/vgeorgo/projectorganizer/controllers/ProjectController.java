package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.exceptions.business.ResourceNotFoundException;
import com.vgeorgo.projectorganizer.models.Project;
import com.vgeorgo.projectorganizer.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjectRepository repository;

    private Project loadResource(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    @GetMapping("/projects")
    public List<Project> all() {
        return repository.findAll();
    }

    @GetMapping("/projects/{id}")
    public Project find(@PathVariable(value = "id") Long id) {
        return loadResource(id);
    }

    @PostMapping("/projects")
    public Project store(@Valid @RequestBody Project project) {
        return repository.save(project);
    }

    @PutMapping("/projects/{id}")
    public Project update(@PathVariable(value = "id") Long id, @Valid @RequestBody Project project) {
        Project updateProject = loadResource(id);

        updateProject.setName(project.getName());
        // Relations
        updateProject.setLeader(project.getLeader());

        updateProject = repository.save(updateProject);
        return repository.refresh(updateProject);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        Project project = loadResource(id);
        repository.delete(project);

        return ResponseEntity.ok().build();
    }
}
