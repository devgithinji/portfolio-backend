package com.densoft.portfolio.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MessageDTO {

    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;

    @NotBlank(message = "message is required")
    private String message;
}
