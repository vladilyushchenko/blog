package com.leverx.blog.service;

import org.springframework.data.util.Pair;

import java.util.List;

public interface TagService {

    List<Pair<String, Integer>> findTagsCount();
}
