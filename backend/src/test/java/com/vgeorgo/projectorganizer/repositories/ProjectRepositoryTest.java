package com.vgeorgo.projectorganizer.repositories;

import com.vgeorgo.projectorganizer.models.Project;
import com.vgeorgo.projectorganizer.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProjectRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository repository;

    @Test
    public void whenFindLast_thenReturnProject() {
        // given
        Project project = new Project();
        project.setName("project");

        entityManager.persist(project);
        entityManager.flush();

        // when
        Project found = repository.findTopByOrderByIdDesc();

        // then
        assertEquals(found.getName(), project.getName());
    }
}