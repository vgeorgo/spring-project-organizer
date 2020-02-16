package com.vgeorgo.projectorganizer.repositories;

import com.vgeorgo.projectorganizer.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void whenFindLast_thenReturnUser() {
        // given
        User user = new User();
        user.setName("user");
        user.setType(User.DEVELOPER);

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = repository.findTopByOrderByIdDesc();

        // then
        assertEquals(found.getName(), user.getName());
    }
}