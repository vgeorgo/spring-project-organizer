package com.vgeorgo.projectorganizer.validators.user;

import com.vgeorgo.projectorganizer.models.User;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = SupervisorValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface SupervisorValidation {
    String message() default "Invalid supervisor";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends User> value() default User.class;
}
