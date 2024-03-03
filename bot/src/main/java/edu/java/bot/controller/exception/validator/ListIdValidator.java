package edu.java.bot.controller.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ListIdValidator implements ConstraintValidator<ListIdPositive, List<Long>> {
    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext context) {
        for (Long id : ids) {
            if (id < 0) {
                return false;
            }
        }

        return true;
    }
}
