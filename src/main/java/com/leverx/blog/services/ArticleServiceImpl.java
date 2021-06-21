package com.leverx.blog.services;

import com.leverx.blog.dao.ArticleDao;
import com.leverx.blog.dao.UserDao;
import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.mapping.ArticleMapping;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.Role;
import com.leverx.blog.entities.User;
import com.leverx.blog.entities.enums.ArticleStatus;
import com.leverx.blog.exceptions.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDao articleDao;
    private final UserService userService;

    public ArticleServiceImpl(ArticleDao articleDao, UserDao userDao, UserService userService) {
        this.articleDao = articleDao;
        this.userService = userService;
    }

    @Override
    public Article findById(int id) {
        Optional<Article> article = articleDao.findById(id);
        throwIfArticleNotFound(article, String.format("There is no article with id %d", id));
        return article.get();
    }

    @Override
    public void save(ArticleDto articleDto) {
        Article article = ArticleMapping.mapToEntity(articleDto);
        article.setAuthorId(getUserIdByEmail(articleDto.getAuthorEmail()));
        article.setCreatedAt(new Date());
        article.setStatus(ArticleStatus.PUBLIC);
        articleDao.save(article);
    }

    @Override
    public void updateById(ArticleDto articleDto, int id) {
        Optional<Article> articleOpt = articleDao.findById(id);
        throwIfArticleNotFound(articleOpt, String.format("There is no article with id %d", id));
        Article article = articleOpt.get();
        User editor = userService.findByEmail(articleDto.getAuthorEmail());
        if (editor.getRoles().contains(Role.ADMIN_ROLE) || article.getAuthorId() != editor.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
        article.setText(articleDto.getText());
        article.setTitle(articleDto.getTitle());
        article.setUpdatedAt(new Date());
        articleDao.update(article);
    }

    @Override
    public List<Article> findAll() {
        Optional<List<Article>> articles = articleDao.findAll();
        if (articles.isEmpty()) {
            throw new NotFoundException("No articles found :(");
        }
        return articles.get();
    }

    @Override
    public List<Article> findArticlesByEmail(String email) {
        Optional<List<Article>> articles = articleDao.findByAuthorId(getUserIdByEmail(email));
        return articles.get();
    }

    @Override
    public void deleteById(int id, String userEmail) {
        assertArticleExistsAndUserHasAccess(userEmail, id);
        articleDao.deleteById(id);
    }

    private int getUserIdByEmail(String email) {
        return userService.findByEmail(email).getId();
    }

    private void assertArticleExistsAndUserHasAccess(String email, int articleId) {
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (articleOpt.isEmpty()) {
            throw new NotFoundException(String.format("No article with id %d", articleId));
        }
        User user = userService.findByEmail(email);
        if (!user.getRoles().contains(Role.ADMIN_ROLE) && articleOpt.get().getAuthorId() != user.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
    }

    private void throwIfArticleNotFound(Optional<Article> article, String text) throws NotFoundException {
        if (article.isEmpty()) {
            throw new NotFoundException(text);
        }
    }
}
