package com.leverx.blog.services;

import com.leverx.blog.entities.Comment;

public interface CommentService {
    Comment findCommentById(int id);
}
