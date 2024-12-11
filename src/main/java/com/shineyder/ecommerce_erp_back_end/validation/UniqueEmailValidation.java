package com.shineyder.ecommerce_erp_back_end.validation;

import com.shineyder.ecommerce_erp_back_end.constraint.UniqueEmailConstraint;
import com.shineyder.ecommerce_erp_back_end.model.Users;
import com.shineyder.ecommerce_erp_back_end.repository.UsersRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@SuppressWarnings("unused")
public class UniqueEmailValidation implements ConstraintValidator<UniqueEmailConstraint, String> {
    private final UsersRepository repository;

    public UniqueEmailValidation(UsersRepository repository){
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueEmailConstraint email) {
    }

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
        Users user = repository.findByEmail(emailField);
        return user == null;
    }
}
