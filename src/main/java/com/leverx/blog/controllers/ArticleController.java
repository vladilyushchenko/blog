package com.leverx.blog.controllers;

import com.leverx.blog.entities.Article;
import com.leverx.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable("id") int id, Principal principal) {
        return new ResponseEntity<>(articleService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticle(@PathVariable("id") int id, Principal principal) {
        return principal.getName();
        //return HttpStatus.OK;
    }
}
