package com.leverx.blog.controllers;

import com.leverx.blog.services.TagService;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags-cloud")
    public ResponseEntity<List<Pair<String, Integer>>> getTagCount() {
        return ResponseEntity.ok(tagService.findTagsCount());
    }
}
