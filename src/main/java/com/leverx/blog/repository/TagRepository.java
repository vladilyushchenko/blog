package com.leverx.blog.repository;

import com.leverx.blog.entity.Tag;
import com.leverx.blog.repository.projections.TagCountProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {

   @Query(value = "SELECT t.name AS tagName, count(t.name) AS tagCount"
           + " FROM  tags_articles ta JOIN  tags t ON ta.tag_id = t.id"
           + " JOIN  articles a ON ta.article_id =a.id GROUP BY t.name", nativeQuery = true)
   List<TagCountProjection> countTags();

}
