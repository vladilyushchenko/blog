package com.leverx.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordResetDto {
    @NotEmpty
    private String hash;

    @NotEmpty
    private String password;
}
