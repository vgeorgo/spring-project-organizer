package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.factories.UserFactory;
import com.vgeorgo.projectorganizer.models.User;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Test
    public void shouldBeOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenListAll_shouldReturn() throws Exception {
        repository.save(UserFactory.createDeveloper("Fred"));
        repository.save(UserFactory.createSupervisor("Johnny"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Fred"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Johnny"));
    }

    @Test
    public void whenFind_shouldReturn() throws Exception {
        repository.save(UserFactory.createSupervisor("Fred"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fred"));
    }

    @Test
    public void whenCreate_shouldReturn() throws Exception {
        User user = UserFactory.createDeveloper("Fred");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fred"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(User.DEVELOPER));
    }

    @Test
    public void whenCreateNoType_shouldError() throws Exception {
        User user = UserFactory.createUser("Fred", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenUpdate_shouldReturn() throws Exception {
        repository.save(UserFactory.createSupervisor("Fred"));
        User user = UserFactory.createSupervisor("Freddy");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Freddy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(User.SUPERVISOR));
    }

    @Test
    public void whenUpdateSelfSupervisor_shouldError() throws Exception {
        User user = UserFactory.createSupervisor("Fred");
        repository.save(user);

        User selfUser = new User();
        selfUser.setId(1L);
        user.setSupervisor(selfUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenUpdateNoExist_shouldError() throws Exception {
        User user = UserFactory.createSupervisor("Freddy");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenCreateWithSupervisor_shouldReturn() throws Exception {
        User supervisor = UserFactory.createSupervisor("Sup");
        repository.save(supervisor);

        User user = UserFactory.createDeveloper("Dev", supervisor);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Dev"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(User.DEVELOPER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supervisor.name").value("Sup"));
    }

    @Test
    public void whenUpdateWithSupervisor_shouldReturn() throws Exception {
        repository.save(UserFactory.createDeveloper("Fred"));
        User supervisor = UserFactory.createSupervisor("Sup");
        repository.save(supervisor);

        User user = UserFactory.createDeveloper("Freddy", supervisor);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Freddy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(User.DEVELOPER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supervisor.name").value("Sup"));
    }

    @Test
    public void whenCreateWithInvalidSupervisor_shouldError() throws Exception {
        User invalidSupervisor = UserFactory.createDeveloper("Sup");
        repository.save(invalidSupervisor);

        User user = UserFactory.createDeveloper("Fred", invalidSupervisor);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encode(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}