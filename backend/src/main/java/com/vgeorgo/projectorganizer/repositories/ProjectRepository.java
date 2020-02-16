package com.vgeorgo.projectorganizer.repositories;

import com.vgeorgo.projectorganizer.models.Project;
import com.vgeorgo.projectorganizer.support.repositories.CustomRepositoryInterface;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CustomRepositoryInterface<Project, Long> {
    Project findTopByOrderByIdDesc();
}
