package com.vgeorgo.projectorganizer.controllers;

import com.vgeorgo.projectorganizer.AbstractControllerTest;
import com.vgeorgo.projectorganizer.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class DeveloperControllerTest extends AbstractControllerTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void firstCreate() throws Exception {
        User user = new User();
        user.setName("Fred");
        user.setType(User.DEVELOPER);

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(201, status);

        String content = mvcResult.getResponse().getContentAsString();
        User saved = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getName(), saved.getName());
    }

    @Test
    public void secondGetList() throws Exception {
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get("/developers")
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        User[] list = super.mapFromJson(content, User[].class);
        Assert.assertTrue(list.length > 0);
    }

    @Test
    public void thirdUpdate() throws Exception {
        User user = new User();
        user.setName("Lemon");

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.put("/users/1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        User saved = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getName(), saved.getName());
    }

    @Test
    public void fourthDelete() throws Exception {
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.delete("/users/1"))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
    }
}