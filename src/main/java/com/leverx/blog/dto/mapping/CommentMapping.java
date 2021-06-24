package com.leverx.blog.dto.mapping;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.entities.Comment;

public class CommentMapping {
    public static Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setAuthorId(commentDto.getAuthorId());
        comment.setMessage(commentDto.getMessage());
        comment.setArticleId(commentDto.getArticleId());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setId(commentDto.getId());
        return comment;
    }

    public static CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setArticleId(comment.getArticleId());
        commentDto.setMessage(comment.getMessage());
        commentDto.setId(comment.getId());
        commentDto.setAuthorId(comment.getAuthorId());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }
}
