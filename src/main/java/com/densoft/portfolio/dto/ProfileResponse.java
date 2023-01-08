package com.densoft.portfolio.dto;

import com.densoft.portfolio.model.User;
import com.densoft.portfolio.utils.AWSS3Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class ProfileResponse {
    private Integer id;
    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private List<String> socialMediaLinks;

    private String resume;

    private String personalStatement;

    private List<String> skills;


    public ProfileResponse(User user, ObjectMapper objectMapper) throws JsonProcessingException {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.socialMediaLinks = objectMapper.readValue(user.getSocialMediaLinks(), List.class);
        this.resume = user.getResumePath();
        this.personalStatement = user.getPersonalStatement();
        this.skills = objectMapper.readValue(user.getSkills(), List.class);
    }

    public String getResume() {
        return AWSS3Util.getFileUrl(resume);
    }
}
