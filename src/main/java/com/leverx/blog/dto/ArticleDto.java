package com.leverx.blog.dto;

import com.leverx.blog.entities.Tag;
import com.leverx.blog.entities.enums.ArticleStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class ArticleDto {
    private int id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String text;

    private ArticleStatus status;

    private int authorId;

    private Date createdAt;

    private Date updatedAt;

    private Set<Tag> tags;
}
