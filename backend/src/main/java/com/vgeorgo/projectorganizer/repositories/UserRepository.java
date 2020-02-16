package com.vgeorgo.projectorganizer.repositories;

import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.support.repositories.CustomRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CustomRepositoryInterface<User, Long> {
    Optional<User> findByIdAndType(Long id, String type);
    List<User> findAllByType(String type);
    User findTopByOrderByIdDesc();
}
