package com.vgeorgo.projectorganizer.factories;

import com.vgeorgo.projectorganizer.models.Project;
import com.vgeorgo.projectorganizer.models.User;

public class ProjectFactory {

    public static Project create(String name, User leader) {
        Project project = new Project();
        project.setName(name);
        project.setLeader(leader);

        return project;
    }

    public static Project create(String name) {
        return create(name, null);
    }

}
