package com.densoft.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseDTO {

    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;
    @NotBlank(message = "email is required")
    @Email(message = "valid email is required")
    private String email;
    @NotBlank(message = "phone is required")
    private String phone;
    @NotBlank(message = "social media links required")
    @Size(max = 180, message = "social media links should be less than 180 characters")
    private String[] socialMediaLinks;
    @NotBlank(message = "personal statement is required")
    @Size(message = "statement should be less than 250 characters", max = 250)
    private String personalStatement;
    @NotBlank(message = "skills required")
    @Size(max = 900, message = "skills should be less than 180 characters")
    private String[] skills;

}
