package com.leverx.blog.auth.registration;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegistrationForm {
    private String firstName;

    private String lastName;

    private String password;

    private String email;


}
