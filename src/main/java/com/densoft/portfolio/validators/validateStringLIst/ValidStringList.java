package com.densoft.portfolio.validators.validateStringLIst;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringLIstValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStringList {
    String message() default "invalid values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
