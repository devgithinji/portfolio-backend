package com.densoft.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @NotNull(message = "social media links required")
    private List<@NotBlank(message = "social media links required") String> socialMediaLinks;
    @NotBlank(message = "personal statement is required")
    @Size(message = "statement should be less than 250 characters", max = 1000)
    private String personalStatement;
    @NotNull(message = "skills required")
    private List<@NotBlank(message = "skills required") String> skills;

}
