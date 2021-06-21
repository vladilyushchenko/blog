package com.leverx.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PasswordResetDto {
    @NotNull
    private Integer hash;

    @NotNull
    @NotEmpty
    private String password;
}
