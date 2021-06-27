package com.leverx.blog.controller;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.dto.CommentPaginationDto;
import com.leverx.blog.entity.enums.CommentSortField;
import com.leverx.blog.entity.enums.Order;
import com.leverx.blog.service.CommentService;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.leverx.blog.controller.FilterConstants.*;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentDto> postCommentByArticleId(@PathVariable int articleId,
                                                             @Valid @RequestBody CommentDto commentDto,
                                                             Principal principal) {
        commentDto.setArticleId(articleId);
        commentDto.setAuthorId(userService.findIdByEmail(principal.getName()));
        return ResponseEntity.ok(commentService.save(commentDto));

    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentsByArticleId(@PathVariable int commentId) {
        return ResponseEntity.ok(commentService.findCommentById(commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable int commentId, Principal principal) {
        commentService.deleteById(commentId, principal.getName());
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> getFilteredComments(
                                @PathVariable int articleId,
                                @RequestParam(value = "skip", defaultValue = DEFAULT_SKIP) int skip,
                                @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT) int limit,
                                @RequestParam(value = "author", required = false) Integer authorId,
                                @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) CommentSortField sortField,
                                @RequestParam(value = "order", defaultValue = DEFAULT_ORDER) Order order) {
        Pageable pageable = PageRequest.of(skip, limit, Sort.by(
                Sort.Direction.valueOf(order.name().toUpperCase()
                ), sortField.name()));
        CommentPaginationDto paginationDto = CommentPaginationDto.builder()
                .authorId(authorId)
                .articleId(articleId)
                .pageable(pageable).build();
        return ResponseEntity.ok(commentService.findAllByPaginationDto(paginationDto));
    }
}
