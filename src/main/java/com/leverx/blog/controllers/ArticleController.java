package com.leverx.blog.controllers;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.entities.Article;
import com.leverx.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/my")
    public ResponseEntity<List<Article>> getMyArticles(Principal principal) {
        return new ResponseEntity<>(articleService.findArticlesByEmail(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return new ResponseEntity<>(articleService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/articles")
    public void postArticle(@Valid @RequestBody ArticleDto articleDto, Principal principal) {
        articleDto.setAuthorEmail(principal.getName());
        articleService.save(articleDto);
    }

    @PutMapping("/articles/{id}")
    public void updateArticle(@RequestBody ArticleDto articleDto,
                              @PathVariable("id") int id,
                              Principal principal) {
        articleDto.setAuthorEmail(principal.getName());
        articleService.updateById(articleDto, id);
    }

    @DeleteMapping("/articles/{id}")
    public void  deleteArticle(@PathVariable("id") int id, Principal principal) {
        articleService.deleteById(id, principal.getName());
    }
}
