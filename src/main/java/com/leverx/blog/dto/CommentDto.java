package com.leverx.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CommentDto {
    private int id;
    @NotEmpty
    @NotNull
    private String message;

    private int articleId;

    private int authorId;

    private Date createdAt;
}
