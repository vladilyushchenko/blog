package com.leverx.blog.service.impl.entitiy;

import com.leverx.blog.dto.TagsCountDto;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.mapper.TagCountMapper;
import com.leverx.blog.repository.TagRepository;
import com.leverx.blog.repository.projections.TagCountProjection;
import com.leverx.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagCountMapper tagCountMapper;

    @Override
    public List<TagsCountDto> findTagsCount() {
        List<TagCountProjection> projections = tagRepository.countTags();
        return projections.stream()
                .map(tagCountMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void initTagsIfNotExist(Set<Tag> tags) {
        if (tags != null) {
            deleteExistingTags(tags);
            tagRepository.saveAll(tags);
        }
    }

    private void deleteExistingTags(Set<Tag> newTags) {
        Set<Tag> allTags = new HashSet<>();
        tagRepository.findAll().forEach(allTags::add);
        newTags.forEach(tag -> {
            if (allTags.contains(tag)) {
                newTags.remove(tag);
            }
        });
    }
}
