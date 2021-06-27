package com.leverx.blog.controller;

import com.leverx.blog.dto.TagsCountDto;
import com.leverx.blog.service.TagService;
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
    public ResponseEntity<List<TagsCountDto>> getTagsCount() {
        return ResponseEntity.ok(tagService.findTagsCount());
    }
}
