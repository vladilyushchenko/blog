package com.leverx.blog.repositories;

import com.leverx.blog.entities.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Integer> {
   Optional<Tag> findByName(String name);

   @Query(value = "SELECT t.name, count(t.name) FROM  tags_articles ta JOIN  tags t ON ta.tag_id = t.id"+
           " JOIN  articles a ON ta.article_id =a.id GROUP BY t.name", nativeQuery = true)
   String[][] countTags();
}
