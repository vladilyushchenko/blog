package com.leverx.blog.dto;

import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.enums.ArticleStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
public class ArticleDto {
    private int id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    private ArticleStatus status;

    private int authorId;

    private Date createdAt;

    private Date updatedAt;

    private Set<Tag> tags;
}
