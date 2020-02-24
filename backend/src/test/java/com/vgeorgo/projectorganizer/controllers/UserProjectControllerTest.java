package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.factories.ProjectFactory;
import com.vgeorgo.projectorganizer.factories.UserFactory;
import com.vgeorgo.projectorganizer.models.Project;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.ProjectRepository;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void whenAddedDeveloperAsDeveloper_shouldReturn() throws Exception {
        userRepository.save(UserFactory.createDeveloper("Fred"));
        projectRepository.save(ProjectFactory.create("Project"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/developers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.developers[0].name").value("Fred"));
    }

    @Test
    public void whenAddedSupervisorAsDeveloper_shouldError() throws Exception {
        userRepository.save(UserFactory.createSupervisor("Fred"));
        projectRepository.save(ProjectFactory.create("Project"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/developers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenAddedDeveloperNonExistingProject_shouldError() throws Exception {
        userRepository.save(UserFactory.createDeveloper("Fred"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/developers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenAddedDeveloperWithSupervisor_shouldReturn() throws Exception {
        User supervisor = UserFactory.createSupervisor("Supervisor");
        userRepository.save(supervisor);

        userRepository.save(UserFactory.createDeveloper("Fred", supervisor));
        projectRepository.save(ProjectFactory.create("Project"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/developers/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.developers[0].name").value("Fred"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.leader.name").value("Supervisor"));
    }

    @Test
    public void whenDeletedDeveloper_shouldReturn() throws Exception {
        User user = UserFactory.createDeveloper("Dev 1");
        User user2 = UserFactory.createDeveloper("Dev 2");
        userRepository.save(user);
        userRepository.save(user2);

        Project project = ProjectFactory.create("Project");
        projectRepository.save(project);

        project.addDeveloper(user);
        project.addDeveloper(user2);
        projectRepository.save(project);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projects/1/developers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.developers", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developers[0].name").value("Dev 2"));
    }

    @Test
    public void whenAddedSupervisorAsSupervisor_shouldReturn() throws Exception {
        userRepository.save(UserFactory.createSupervisor("Supervisor"));
        projectRepository.save(ProjectFactory.create("Project"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/supervisors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.leader.name").value("Supervisor"));
    }

    @Test
    public void whenAddedDeveloperAsSupervisor_shouldReturn() throws Exception {
        userRepository.save(UserFactory.createDeveloper("Fred"));
        projectRepository.save(ProjectFactory.create("Project"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/supervisors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenAddedSupervisorNonExistingProject_shouldError() throws Exception {
        userRepository.save(UserFactory.createDeveloper("Fred"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/supervisors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenDeletedSupervisor_shouldReturn() throws Exception {
        User user = UserFactory.createSupervisor("Supervisor");
        userRepository.save(user);

        Project project = ProjectFactory.create("Project");
        projectRepository.save(project);

        project.setLeader(user);
        projectRepository.save(project);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projects/1/supervisors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.leader").isEmpty());
    }

}