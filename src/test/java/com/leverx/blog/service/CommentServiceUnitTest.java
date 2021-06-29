package com.leverx.blog.service;

import com.leverx.blog.entity.Comment;
import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.mapper.CommentMapper;
import com.leverx.blog.repository.CommentRepository;
import com.leverx.blog.service.impl.entitiy.CommentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class CommentServiceUnitTest {
    private final CommentRepository mockCommentRepository = Mockito.mock(CommentRepository.class);
    private final UserService mockUserService = Mockito.mock(UserService.class);
    private final ArticleService mockArticleService = Mockito.mock(ArticleService.class);
    private final CommentService service = new CommentServiceImpl(mockCommentRepository, mockUserService,
            mockArticleService, new CommentMapper());

    @Test
    public void findCommentById_ShouldReturnExistingComment() {
        Comment existing = getExistingComment();
        when(mockCommentRepository.findById(existing.getId())).thenReturn(Optional.of(existing));
        Assertions.assertEquals(new CommentMapper().mapToDto(existing), service.findCommentById(existing.getId()));
    }

    @Test
    public void findCommentById_ShouldThrowNotExistsExceptionByNonExistingId() {
        Comment nonExisting = getNonExistingComment();
        when(mockCommentRepository.findById(nonExisting.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundEntityException.class, () -> service.findCommentById(nonExisting.getId()));
    }

    private Comment getNonExistingComment() {
        return new Comment(-1, "no such text", -1, -1, new Date());
    }

    private Comment getExistingComment() {
        return new Comment(1, "test comment#1 for post #1", 1, 1, new Date());
    }

}