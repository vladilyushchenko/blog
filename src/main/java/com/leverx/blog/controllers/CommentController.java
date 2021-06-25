package com.leverx.blog.controllers;

import com.leverx.blog.dto.CommentDto;
import com.leverx.blog.dto.CommentPaginationDto;
import com.leverx.blog.entities.enums.CommentSortField;
import com.leverx.blog.entities.enums.Order;
import com.leverx.blog.services.CommentService;
import com.leverx.blog.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.leverx.blog.controllers.FilterConstants.*;

@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> postCommentByArticleId(@PathVariable int articleId,
                                                             @Valid @RequestBody CommentDto commentDto,
                                                             Principal principal) {
        commentDto.setArticleId(articleId);
        commentDto.setAuthorId(userService.findIdByEmail(principal.getName()));
        return ResponseEntity.ok(commentService.save(commentDto));

    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentsByArticleId(@PathVariable int articleId,
                                                          @PathVariable int commentId) {
        return ResponseEntity.ok(commentService.findCommentById(commentId));
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable int articleId,
                              @PathVariable int commentId,
                              Principal principal) {
        commentService.deleteById(commentId, principal.getName());
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getFilteredComments(
                                @PathVariable int articleId,
                                @RequestParam(value = "skip", defaultValue = DEFAULT_SKIP) int skip,
                                @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT) int limit,
                                @RequestParam(value = "author") int authorId,
                                @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) CommentSortField sortField,
                                @RequestParam(value = "order", defaultValue = DEFAULT_ORDER) Order order) {
        CommentPaginationDto paginationDto = CommentPaginationDto.builder()
                .skip(skip)
                .limit(limit)
                .authorId(authorId)
                .articleId(articleId)
                .sortField(sortField)
                .order(order).build();
        return new ResponseEntity<>(commentService.findAllByPaginationDto(paginationDto), HttpStatus.OK);
    }
}
