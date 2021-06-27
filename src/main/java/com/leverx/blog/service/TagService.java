package com.leverx.blog.service;

import com.leverx.blog.dto.TagsCountDto;
import com.leverx.blog.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {

    List<TagsCountDto> findTagsCount();

    void initTagsIfNotExist(Set<Tag> tags);
}
