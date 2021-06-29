package com.leverx.blog.service.impl.entitiy;

import com.leverx.blog.dto.TagsCountDto;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.mapper.TagCountMapper;
import com.leverx.blog.repository.TagRepository;
import com.leverx.blog.repository.projections.TagCountProjection;
import com.leverx.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagCountMapper tagCountMapper;

    @Override
    public List<TagsCountDto> findTagsCount() {
        log.info("COUNTING TAGS WITH ITS COUNT");
        List<TagCountProjection> projections = tagRepository.countTags();
        return projections.stream()
                .map(tagCountMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void initTagsIfNotExist(Set<Tag> tags) {
        if (tags != null) {
            Set<Tag> newTags = getNewTagsAndInit(tags);
            tagRepository.saveAll(newTags);
        }
    }

    private Set<Tag> getNewTagsAndInit(Set<Tag> tags) {
        log.info("ADDING NEW TAGS TO DATABASE");

        Map<String, Integer> tagIdByName = tagsListToMap(tagRepository.findAll());
        Set<Tag> newTags = new HashSet<>();

        tags.forEach(tag -> {
            if (tagIdByName.containsKey(tag.getName())) {
                tag.setId(tagIdByName.get(tag.getName()));
            } else {
                newTags.add(tag);
            }
         });
        return newTags;
    }

    private Map<String, Integer> tagsListToMap(List<Tag> tags) {
        Map<String, Integer> map = new HashMap<>();
        tags.forEach(tag -> map.put(tag.getName(), tag.getId()));
        return map;
    }
}
