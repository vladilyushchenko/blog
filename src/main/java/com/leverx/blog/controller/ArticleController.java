package com.leverx.blog.controller;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.entity.enums.ArticleSortField;
import com.leverx.blog.entity.enums.Order;
import com.leverx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/my")
    public ResponseEntity<List<ArticleDto>> getMyArticles(Principal principal) {
        return ResponseEntity.ok(articleService.findArticlesByEmail(principal.getName()));
    }

    @GetMapping("/articles_by")
    public ResponseEntity<List<ArticleDto>> getArticlesByTags(@RequestParam("tags") String[] tagNames) {
        return ResponseEntity.ok(articleService.findAllByTagNames(tagNames));
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDto>> getFilteredArticles(
            @RequestParam(value = "skip", defaultValue = DEFAULT_SKIP) int skip,
            @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT) int limit,
            @RequestParam(value = "author", required = false) Integer authorId,
            @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) ArticleSortField sortField,
            @RequestParam(value = "order", defaultValue = DEFAULT_ORDER) Order order) {
        Pageable pageable = PageRequest.of(skip, limit, Sort.by(
                Sort.Direction.valueOf(order.name().toUpperCase()), sortField.name())
        );
        List<ArticleDto> dtos = articleService.findAllByAuthorAndPageable(authorId, pageable);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleDto> postArticle(@Valid @RequestBody ArticleDto articleDto, Principal principal) {
        return ResponseEntity.ok(articleService.save(articleDto, principal.getName()));
    }

    @PutMapping("/articles/{id}")
    public void updateArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable int id,
                              Principal principal) {

        articleService.updateById(articleDto, id, principal.getName());
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable int id, Principal principal) {
        articleService.deleteById(id, principal.getName());
    }
}

