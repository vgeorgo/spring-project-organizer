package com.vgeorgo.projectorganizer.validators.user;

import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupervisorValidator {

    private static UserRepository repository;

    @Autowired
    public SupervisorValidator(UserRepository repository) {
        SupervisorValidator.repository = repository;
    }

    public static boolean isValid(User user, Long parentId) {
        if(user == null)
            return true;

        if(user.getId() == null || user.getId().equals(parentId))
            return false;

        // Validates if the user really is a supervisor
        return repository.findByIdAndType(user.getId(), User.SUPERVISOR).isPresent();
    }

    public static boolean isValid(User user) {
        return isValid(user, null);
    }

}
