package com.leverx.blog.entities;

import java.util.Date;

public class Comment {
    private int id;
    private String message;
    private int articleId;
    private int authorId;
    private Date createdAt;
}