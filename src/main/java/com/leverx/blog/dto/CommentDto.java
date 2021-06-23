package com.leverx.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotEmpty
    @NotNull
    private String message;

    @NotEmpty
    @NotNull
    private int articleId;

    @NotEmpty
    @NotNull
    private int authorId;
}
