package com.leverx.blog.service.impl.entitiy;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.dto.CommentPaginationDto;
import com.leverx.blog.entity.Comment;
import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.mapper.CommentMapper;
import com.leverx.blog.mapper.UserMapper;
import com.leverx.blog.repository.CommentRepository;
import com.leverx.blog.service.CommentService;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto findCommentById(int id) {
        // TODO: ORELSETHROW
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            throw new NotFoundEntityException(Comment.class, id);
        }
        return commentMapper.mapToDto(commentOpt.get());
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        Comment newComment = commentMapper.mapToEntity(commentDto);
        newComment.setCreatedAt(new Date());
        commentRepository.save(newComment);
        return commentMapper.mapToDto(newComment);
    }

    @Override
    public List<CommentDto> findCommentsByArticleId(int articleId) {
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);
        return comments.stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id, String editorEmail) {
        int editorId = userService.findIdByEmail(editorEmail);
        // ORELSETHROW
        Optional<Integer> authorId = commentRepository.findAuthorIdByCommentId(id);
        if (authorId.isEmpty()) {
            throw new NotFoundEntityException(Comment.class, id);
        }
        if (editorId != authorId.get()) {
            throw new AccessDeniedException("This comment doesn't belong to you!");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findAllByPaginationDto(CommentPaginationDto paginationDto) {
        return getComments(Optional.ofNullable(paginationDto.getAuthorId()),
                paginationDto.getArticleId(), paginationDto.getPageable()).stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private List<Comment> getComments(Optional<Integer> authorId, int articleId, Pageable pageable) {
        if (authorId.isEmpty()) {
            return commentRepository.findAllByArticleId(articleId);
        }
        return commentRepository.findAllByAuthorIdAndArticleId(authorId.get(), articleId, pageable);
    }
}
