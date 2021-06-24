package com.leverx.blog.dto;

import com.leverx.blog.entities.Role;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

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

    private Collection<Role> roles;
}


