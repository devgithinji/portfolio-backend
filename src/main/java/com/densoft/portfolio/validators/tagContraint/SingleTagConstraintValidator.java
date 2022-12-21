package com.densoft.portfolio.validators.tagContraint;

import com.densoft.portfolio.service.tags.TagsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

public class SingleTagConstraintValidator implements ConstraintValidator<SingleTagValidator, String> {

    @Autowired
    private TagsService tagsService;

    private List<String> tagList;

    @Override
    public void initialize(SingleTagValidator constraintAnnotation) {
        tagList = tagsService.getTags().stream().map(tag -> tag.getName().toLowerCase()).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {

        if (string == null || string.isBlank()) return false;
        return tagList.contains(string.toLowerCase());
    }
}
