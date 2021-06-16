package com.leverx.blog.model;

import java.util.Date;

public class Article {
    private int id;
    private String title;
    private String text;
    private ArticleStatus status;
    private int authorId;
    private Date createdAt;
    private Date updatedAt;
}
