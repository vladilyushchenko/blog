package com.leverx.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ArticleDto {
    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String text;

    private String authorEmail;
}
