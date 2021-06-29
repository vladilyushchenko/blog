package com.leverx.blog.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Builder
@Data
public class CommentPaginationDto {
    private final Integer authorId;
    private final Integer articleId;
    private final Pageable pageable;
}
