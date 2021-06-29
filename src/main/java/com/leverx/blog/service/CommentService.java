package com.leverx.blog.service;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.dto.CommentPaginationDto;

import java.util.List;

public interface CommentService {
    CommentDto findCommentById(int id);

    CommentDto save(CommentDto commentDto);

    List<CommentDto> findCommentsByArticleId(int articleId);

    void deleteById(int id, String editorEmail);

    List<CommentDto> findAllByPaginationDto(CommentPaginationDto paginationDto);
}
