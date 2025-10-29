package com.tytran.blog.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BirthdayValidator implements ConstraintValidator<BirthdayConstraint, LocalDate> {

    private int min;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(Objects.isNull(value)){
            return true;
        }

        long years = ChronoUnit.YEARS.between(value, LocalDate.now());

        return years >= min;
    }

    @Override
    public void initialize(BirthdayConstraint constraint) {
        ConstraintValidator.super.initialize(constraint);
        min = constraint.min();
    }

}
