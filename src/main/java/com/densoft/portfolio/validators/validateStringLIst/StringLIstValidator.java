package com.densoft.portfolio.validators.validateStringLIst;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class StringLIstValidator implements ConstraintValidator<ValidStringList, List<String>> {
    @Override
    public void initialize(ValidStringList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        if (strings == null || strings.isEmpty()) return false;
        for (String str : strings){
            if (str == null || str.isBlank()) return false;
        }
        return true;
    }
}
