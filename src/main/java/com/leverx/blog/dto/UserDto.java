package com.leverx.blog.dto;

import com.leverx.blog.entities.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
public class UserDto {
    private int id;

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String email;

    private Date createdAt;

    private Set<Role> roles;
}


