package com.densoft.portfolio.validators.fileType;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileValidator.class})
public @interface ValidFile {

    String message() default "Invalid file";

    FileType fileType() default FileType.IMAGE;

    int maxSize() default 5;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

