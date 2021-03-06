package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.factories.UserFactory;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SupervisorControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Test
    public void shouldBeOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/supervisors")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenListSupervisor_shouldReturn() throws Exception {
        repository.save(UserFactory.createSupervisor("Fred"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/supervisors")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Fred"));
    }

    @Test
    public void whenListDeveloper_shouldNotReturn() throws Exception {
        repository.save(UserFactory.createDeveloper("Fred"));
        repository.save(UserFactory.createSupervisor("Johnny"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/supervisors")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Johnny"));
    }

    @Test
    public void whenFindSupervisor_shouldReturn() throws Exception {
        repository.save(UserFactory.createSupervisor("Fred"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/supervisors/1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fred"));;
    }

    @Test
    public void whenFindDeveloper_shouldNotReturn() throws Exception {
        repository.save(UserFactory.createDeveloper("Johnny"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/supervisors/1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}