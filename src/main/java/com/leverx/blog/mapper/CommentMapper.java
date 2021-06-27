package com.leverx.blog.mapper;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setAuthorId(commentDto.getAuthorId());
        comment.setMessage(commentDto.getMessage());
        comment.setArticleId(commentDto.getArticleId());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setId(commentDto.getId());
        return comment;
    }

    public CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setArticleId(comment.getArticleId());
        commentDto.setMessage(comment.getMessage());
        commentDto.setId(comment.getId());
        commentDto.setAuthorId(comment.getAuthorId());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }
}
