package com.vgeorgo.projectorganizer.factories;

import com.vgeorgo.projectorganizer.models.User;

public class UserFactory {

    public static User createUser(String name, String type) {
        User user = new User();
        user.setName(name);
        user.setType(type);

        return user;
    }

    public static User createDeveloper(String name) {
        return createUser(name, User.DEVELOPER);
    }

    public static User createSupervisor(String name) {
        return createUser(name, User.SUPERVISOR);
    }

}
