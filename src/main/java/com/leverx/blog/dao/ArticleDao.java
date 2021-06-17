package com.leverx.blog.dao;

import com.leverx.blog.entities.Article;

public interface ArticleDao {
    Article getById(int id);
}
