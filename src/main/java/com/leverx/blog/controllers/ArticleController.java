package com.leverx.blog.controllers;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.ArticlePaginationDto;
import com.leverx.blog.entities.enums.ArticleSortField;
import com.leverx.blog.entities.enums.Order;
import com.leverx.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.leverx.blog.controllers.FilterConstants.*;

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


    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDto>> getFilteredArticles(
                                @RequestParam(value = "skip", defaultValue = DEFAULT_SKIP) int skip,
                                @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT) int limit,
                                @RequestParam(value = "author") int authorId,
                                @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) ArticleSortField sortField,
                                @RequestParam(value = "order", defaultValue = DEFAULT_ORDER) Order order) {
        ArticlePaginationDto paginationDto = ArticlePaginationDto.builder()
                .skip(skip)
                .limit(limit)
                .authorId(authorId)
                .sortField(sortField)
                .order(order).build();
        return ResponseEntity.ok(articleService.findAllByPaginationDto(paginationDto));
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleDto> postArticle(@Valid @RequestBody ArticleDto articleDto, Principal principal) {
        return ResponseEntity.ok(articleService.save(articleDto, principal.getName()));
    }

    @PutMapping("/articles/{id}")
    public void updateArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable("id") int id,
                              Principal principal) {
        articleService.updateById(articleDto, id, principal.getName());
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable("id") int id, Principal principal) {
        articleService.deleteById(id, principal.getName());
    }
}

