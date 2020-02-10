package com.vgeorgo.projectorganizer.repositories;

import com.vgeorgo.projectorganizer.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
