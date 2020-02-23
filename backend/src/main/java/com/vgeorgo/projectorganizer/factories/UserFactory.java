package com.vgeorgo.projectorganizer.factories;

import com.vgeorgo.projectorganizer.models.User;

public class UserFactory {

    public static User createUser(String name, String type, User supervisor) {
        User user = new User();
        user.setName(name);
        user.setType(type);
        user.setSupervisor(supervisor);

        return user;
    }

    public static User createUser(String name, String type) {
        return createUser(name, type, null);
    }

    public static User createDeveloper(String name) {
        return createUser(name, User.DEVELOPER);
    }

    public static User createDeveloper(String name, User supervisor) {
        return createUser(name, User.DEVELOPER, supervisor);
    }

    public static User createSupervisor(String name) {
        return createUser(name, User.SUPERVISOR);
    }

}
