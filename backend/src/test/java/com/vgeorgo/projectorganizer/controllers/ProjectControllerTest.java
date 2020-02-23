package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.factories.ProjectFactory;
import com.vgeorgo.projectorganizer.factories.UserFactory;
import com.vgeorgo.projectorganizer.models.Project;
import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.ProjectRepository;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import com.vgeorgo.projectorganizer.support.data.Json;
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
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProjectRepository repository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldBeOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenListAll_shouldReturn() throws Exception {
        repository.save(ProjectFactory.create("Project"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Project"));
    }

    @Test
    public void whenFind_shouldReturn() throws Exception {
        repository.save(ProjectFactory.create("Project"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Project"));
    }

    @Test
    public void whenCreate_shouldReturn() throws Exception {
        Project project = ProjectFactory.create("Project");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(project)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Project"));
    }

    @Test
    public void whenCreateNoName_shouldError() throws Exception {
        Project project = ProjectFactory.create(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(project)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenCreateWithSupervisor_shouldReturn() throws Exception {
        User leader = UserFactory.createSupervisor("Fred");
        userRepository.save(leader);

        Project project = ProjectFactory.create("Project", leader);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(project)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.leader.name").value("Fred"));
    }

    @Test
    public void whenCreateWithInvalidSupervisor_shouldError() throws Exception {
        User invalidLeader = UserFactory.createDeveloper("Fred");
        userRepository.save(invalidLeader);

        Project project = ProjectFactory.create("Project", invalidLeader);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(project)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenUpdateWithSupervisor_shouldReturn() throws Exception {
        User leader = UserFactory.createSupervisor("Fred");
        userRepository.save(leader);

        Project project = ProjectFactory.create("Project");
        repository.save(project);

        project.setName("New project");
        project.setLeader(leader);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(project)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.leader.name").value("Fred"));
    }

}