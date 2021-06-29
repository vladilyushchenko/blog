package com.leverx.blog.mapper;

import com.leverx.blog.dto.TagsCountDto;
import com.leverx.blog.repository.projections.TagCountProjection;
import org.springframework.stereotype.Component;

@Component
public class TagCountMapper {
    public TagsCountDto mapToDto(TagCountProjection projection) {
        return new TagsCountDto(projection.getTagName(), projection.getTagCount());
    }
}
