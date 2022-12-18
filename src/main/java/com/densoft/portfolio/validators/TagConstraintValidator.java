package com.densoft.portfolio.validators;

import com.densoft.portfolio.service.tags.TagsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

public class TagConstraintValidator implements ConstraintValidator<TagConstraint, String[]> {

    @Autowired
    private TagsService tagsService;

    private List<String> tagList;

    @Override
    public void initialize(TagConstraint constraintAnnotation) {
        tagList = tagsService.getTags().stream().map(tag -> tag.getName().toLowerCase()).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String[] strings, ConstraintValidatorContext constraintValidatorContext) {

        if(strings == null || strings.length == 0) return false;
        for (String tag : strings) {
            if (!tagList.contains(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
