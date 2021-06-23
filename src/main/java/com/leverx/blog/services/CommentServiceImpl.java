package com.leverx.blog.services;

import com.leverx.blog.entities.Comment;
import com.leverx.blog.exceptions.NotFoundException;
import com.leverx.blog.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findCommentById(int id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            throw new NotFoundException(String.format("There is no comment with id %d", id));
        }
        return commentOpt.get();
    }
}
