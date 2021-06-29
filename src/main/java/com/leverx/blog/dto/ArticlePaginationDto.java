package com.leverx.blog.dto;

import com.leverx.blog.entity.enums.ArticleSortField;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Builder
@Data
public class ArticlePaginationDto {
    private final int skip;
    private final int limit;
    private final Optional<Integer> authorId;
    private final ArticleSortField sortField;
    private final Sort.Direction direction;
}
