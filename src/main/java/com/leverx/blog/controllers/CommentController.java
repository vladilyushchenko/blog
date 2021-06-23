package com.leverx.blog.controllers;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.entities.Comment;
import com.leverx.blog.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/")
    public ResponseEntity<Comment> postCommentByArticleId(@PathVariable int articleId,
                                                          @RequestBody CommentDto commentDto) {
        // service logic...
        return null;
    }
}
