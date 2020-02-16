package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@RestClientTest(DeveloperController.class)
public class DeveloperControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void whenGetDevelopers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setName("user");
        user.setType(User.DEVELOPER);

        entityManager.persist(user);
        entityManager.flush();

        mvc.perform(get("/api/developers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(user.getName())));
    }
}