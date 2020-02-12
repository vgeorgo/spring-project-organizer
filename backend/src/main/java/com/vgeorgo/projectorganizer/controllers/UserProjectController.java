package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.exceptions.business.ResourceNotFoundException;
import com.vgeorgo.projectorganizer.models.Project;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.ProjectRepository;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class UserProjectController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    private User loadResourceUser(Long id, String type) {
        return userRepository.findByIdAndType(id, type)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    private Project loadResourceProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    @PutMapping("/projects/{id}/developers/{idUser}")
    public Project addDeveloper(@PathVariable(value = "id") Long id, @PathVariable(value = "idUser") Long idUser) {
        Project project = loadResourceProject(id);
        User dev = loadResourceUser(idUser, User.DEVELOPER);

        if(dev.getSupervisor() != null && project.getLeader() == null)
            project.setLeader(dev.getSupervisor());
        project.addDeveloper(dev);

        project = projectRepository.save(project);
        return projectRepository.refresh(project);
    }

    @DeleteMapping("/projects/{id}/developers/{idUser}")
    public Project deleteDeveloper(@PathVariable(value = "id") Long id, @PathVariable(value = "idUser") Long idUser) {
        Project project = loadResourceProject(id);
        User dev = loadResourceUser(idUser, User.DEVELOPER);

        project.deleteDeveloper(dev);

        project = projectRepository.save(project);
        return projectRepository.refresh(project);
    }

    @DeleteMapping("/projects/{id}/developers")
    public Project deleteAllDeveloper(@PathVariable(value = "id") Long id) {
        Project project = loadResourceProject(id);

        project.setDevelopers(new ArrayList<User>());

        project = projectRepository.save(project);
        return projectRepository.refresh(project);
    }

    @PutMapping("/projects/{id}/supervisors/{idUser}")
    public Project addSupervisor(@PathVariable(value = "id") Long id, @PathVariable(value = "idUser") Long idUser) {
        Project project = loadResourceProject(id);
        User supervisor = loadResourceUser(idUser, User.SUPERVISOR);

        project.setLeader(supervisor);

        project = projectRepository.save(project);
        return projectRepository.refresh(project);
    }

    @DeleteMapping("/projects/{id}/supervisors")
    public Project deleteSupervisor(@PathVariable(value = "id") Long id) {
        Project project = loadResourceProject(id);

        project.setLeader(null);

        project = projectRepository.save(project);
        return projectRepository.refresh(project);
    }
}
