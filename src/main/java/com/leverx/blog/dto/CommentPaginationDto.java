package com.leverx.blog.dto;

import com.leverx.blog.entity.enums.CommentSortField;
import com.leverx.blog.entity.enums.Order;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentPaginationDto {
    private final int skip;
    private final int limit;
    private final int authorId;
    private final int articleId;
    private final CommentSortField sortField;
    private final Order order;
}
