package com.leverx.blog.service;

import com.leverx.blog.dto.PasswordResetDto;

public interface PasswordService {
    void reset(String email);

    void confirmReset(PasswordResetDto resetDto);
}
