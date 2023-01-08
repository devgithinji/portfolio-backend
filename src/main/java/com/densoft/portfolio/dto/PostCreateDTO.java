package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.tagContraint.SingleTagValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDTO {
    @NotBlank(message = "Post title is required")
    @Size(min = 10, max = 50, message = "Post title should be at least 10 characters")
    private String title;

    @SingleTagValidator
    private String tag;
}
