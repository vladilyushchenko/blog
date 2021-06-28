package com.leverx.blog.controller;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.TagsCountDto;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final ArticleService articleService;

    @GetMapping("/tags-cloud")
    public ResponseEntity<List<TagsCountDto>> getTagsCount() {
        return ResponseEntity.ok(tagService.findTagsCount());
    }

    @GetMapping("/articles_by")
    public ResponseEntity<List<ArticleDto>> getArticlesByTags(@RequestParam("tags") String[] tagNames) {
        return ResponseEntity.ok(articleService.findAllByTagNames(tagNames));
    }
}
