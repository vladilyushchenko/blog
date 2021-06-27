package com.leverx.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TagsCountDto {
    private final String tagName;
    private final int tagCount;
}
