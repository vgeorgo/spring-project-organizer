package com.vgeorgo.projectorganizer.validators.user;

import com.vgeorgo.projectorganizer.models.User;
import com.vgeorgo.projectorganizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SupervisorValidator implements ConstraintValidator<SupervisorValidation, User> {
    @Autowired
    UserRepository repository;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if(user == null)
            return true;

        if(user.getId() == null)
            return false;

        // Validates if the user really is a supervisor
        return repository.findByIdAndType(user.getId(), User.SUPERVISOR).isPresent();
    }
}