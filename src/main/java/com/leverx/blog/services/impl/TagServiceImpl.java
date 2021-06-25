package com.leverx.blog.services.impl;

import com.leverx.blog.repositories.TagRepository;
import com.leverx.blog.services.TagService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Pair<String, Integer>> findTagsCount() {
        String[][] tagsCount = tagRepository.countTags();
        List<Pair<String, Integer>> tagsCountList = new ArrayList<>();
        Arrays.stream(tagsCount).forEach(
                (tagCount) -> tagsCountList.add(Pair.of(tagCount[0], Integer.valueOf(tagCount[1])))
        );
        return tagsCountList;
    }
}
