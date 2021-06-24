package com.leverx.blog.repositories;

import com.leverx.blog.entities.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Integer> {
   Optional<Tag> findByName(String name);
}
