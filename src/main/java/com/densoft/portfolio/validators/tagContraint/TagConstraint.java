package com.densoft.portfolio.validators.tagContraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TagConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagConstraint {
    String message() default "invalid tag";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
