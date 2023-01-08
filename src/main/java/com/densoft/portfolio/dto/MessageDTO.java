package com.densoft.portfolio.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MessageDTO {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;

    @NotBlank(message = "message is required")
    @Size(max = 400, message = "maximum size is 400")
    private String message;
}
