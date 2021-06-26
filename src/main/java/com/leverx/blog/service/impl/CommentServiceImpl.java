package com.leverx.blog.service.impl;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.dto.CommentPaginationDto;
import com.leverx.blog.dto.mapping.CommentMapping;
import com.leverx.blog.entity.Comment;
import com.leverx.blog.entity.enums.Order;
import com.leverx.blog.exception.NotFoundException;
import com.leverx.blog.repository.CommentRepository;
import com.leverx.blog.service.CommentService;
import com.leverx.blog.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.leverx.blog.controller.FilterConstants.ALL_AUTHORS;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public CommentDto findCommentById(int id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            throw new NotFoundException(String.format("There is no comment with id %d", id));
        }
        return CommentMapping.mapToDto(commentOpt.get());
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        Comment newComment = CommentMapping.mapToEntity(commentDto);
        newComment.setCreatedAt(new Date());
        newComment = commentRepository.save(newComment);
        return CommentMapping.mapToDto(newComment);
    }

    @Override
    public List<CommentDto> findCommentsByArticleId(int articleId) {
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
        return comments.stream().map(CommentMapping::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id, String editorEmail) {
        int editorId = userService.findIdByEmail(editorEmail);
        Optional<Integer> authorId = commentRepository.findAuthorIdByCommentId(id);
        if (authorId.isEmpty()) {
            throw new NotFoundException(String.format("There is no article with id %d", id));
        }
        if (editorId != authorId.get()) {
            throw new AccessDeniedException("This comment doesn't belong to you!");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findAllByPaginationDto(CommentPaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getSkip(), paginationDto.getLimit(),
                Sort.by(paginationDto.getSortField().name()));
        List<Comment> comments = getComments(paginationDto, pageable);
        return comments.stream().map(CommentMapping::mapToDto).collect(Collectors.toList());
    }

    private List<Comment> getComments(CommentPaginationDto paginationDto, Pageable pageable) {
        List<Comment> comments;
        if (paginationDto.getAuthorId() == Integer.parseInt(ALL_AUTHORS)) {
            comments = commentRepository.findAllByArticleId(paginationDto.getArticleId());
        } else {
            comments = commentRepository.findAllByAuthorIdAndArticleId(paginationDto.getAuthorId(),
                    paginationDto.getArticleId(), pageable);
        }
        if (paginationDto.getOrder().equals(Order.desc)) {
            Collections.reverse(comments);
        }
        return comments;
    }
}
