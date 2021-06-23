package com.leverx.blog.dto;

import com.leverx.blog.entities.enums.ArticleSortField;
import com.leverx.blog.entities.enums.Order;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticlePaginationDto {
    private final int skip;
    private final int limit;
    private final int authorId;
    private final ArticleSortField sortField;
    private final Order order;
}
